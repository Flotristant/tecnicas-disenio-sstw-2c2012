#include <gtkmm-2.4/gtkmm.h>
#include <iostream>
#include "FFmpegHeaders.h"
#include <stdio.h>
#define INBUF_SIZE 4096
/*
 * Falla cuando se hace click en guardar antes de capturar la pantalla,
 * No importa. por ahora.
 */

void convertirRgbAYuv(int red, int green, int blue, int& y, int& u, int& v) {
	y = (0.257 * red) + (0.504 * green) + (0.098 * blue) + 16;
	v = (0.439 * red) - (0.368 * green) - (0.071 * blue) + 128; // Cr = V
	u = -(0.148 * red) - (0.291 * green) + (0.439 * blue) + 128; // Cb = u
}

void convertirRgbAY(int red, int green, int blue, int& y) {
	y = (0.257 * red) + (0.504 * green) + (0.098 * blue) + 16;
}

void convertirRgbAUv(int red, int green, int blue, int& u, int& v) {
	v = (0.439 * red) - (0.368 * green) - (0.071 * blue) + 128; // Cr = V
	u = -(0.148 * red) - (0.291 * green) + (0.439 * blue) + 128; // Cb = u
}

void encodeImagePixbuf(const char *filename, Gtk::Image* captura) {
	AVCodec *codec;
	AVCodecContext *c = NULL;
	int i, out_size, size, x, y, outbuf_size;
	FILE *f;
	AVFrame *picture;
	uint8_t *outbuf, *picture_buf;

	printf("Video encoding\n");

	// Register all formats and codecs
	av_register_all();

	// Tomo datos del Pixbuf;
	Glib::RefPtr < Gdk::Pixbuf > screenshot = captura->get_pixbuf();
	int width, height, rowstride, n_channels;
	int Y, U, V;
	guchar *pixels, *p;

	n_channels = gdk_pixbuf_get_n_channels(screenshot->gobj());
	width = gdk_pixbuf_get_width(screenshot->gobj());
	height = gdk_pixbuf_get_height(screenshot->gobj());
	rowstride = gdk_pixbuf_get_rowstride(screenshot->gobj());
	pixels = gdk_pixbuf_get_pixels(screenshot->gobj());
	//p = pixels + y * rowstride + x * n_channels;
	// fin Tomar datos.

	/* find the mpeg1 video encoder */
	//CODEC_ID_MPEG2VIDEO
	//*codec = avcodec_find_encoder(CODEC_ID_MPEG4);
	//codec = avcodec_find_encoder(CODEC_ID_MPEG1VIDEO);
	codec = avcodec_find_encoder(CODEC_ID_MPEG1VIDEO);
	if (!codec) {
		fprintf(stderr, "codec not found\n");
		exit(1);
	}

	c = avcodec_alloc_context();
	picture = avcodec_alloc_frame();

	/* put sample parameters */
	c->bit_rate = 400000;
	/* resolution must be a multiple of two */
	//#	c->width = 352;
	//#	c->height = 288;
	c->width = width;
	c->height = height;

	/* frames per second */
	c->time_base = (AVRational) {1,25};
	c->gop_size = 10; /* emit one intra frame every ten frames */
	c->max_b_frames = 1;
	//*c->pix_fmt = PIX_FMT_RGB24;
	//#c->pix_fmt = PIX_FMT_YUV420P;

	c->pix_fmt = PIX_FMT_YUV420P;
	/* open it */
	if (avcodec_open(c, codec) < 0) {
		fprintf(stderr, "could not open codec\n");
		exit(1);
	}

	f = fopen(filename, "wb");
	if (!f) {
		fprintf(stderr, "could not open %s\n", filename);
		exit(1);
	}

	/* alloc image and output buffer */
	outbuf_size = 100000;
	outbuf = (uint8_t*) malloc(outbuf_size);
	size = c->width * c->height;
	picture_buf = (uint8_t*) malloc((size * 3) / 2); /* size for YUV 420 */

	//	picture->data[0] = picture_buf;
	//	picture->data[1] = picture->data[0] + size;
	//	picture->data[2] = picture->data[1] + size / 4;
	//	picture->linesize[0] = c->width;
	//	picture->linesize[1] = c->width / 2;
	//	picture->linesize[2] = c->width / 2;

	avpicture_fill((AVPicture *) picture, picture_buf, PIX_FMT_YUV420P,
			c->width, c->height);

	/* encode 1 second of video */
	//for (i = 0; i < 25; i++) {
	for (i = 0; i < 125; i++) {
		fflush(stdout);

		/* prepare a dummy image */
		/* Y */
		for (y = 0; y < c->height; y++) {
			for (x = 0; x < c->width; x++) {
				//datos pixbuf.
				p = pixels + y * rowstride + x * n_channels;
				//	p[0] -- Red
				//	p[1] -- Green
				//	p[2] -- Blue
				//	p[3] -- Alfa
				//	R = p[0];// = 0; //Red
				//  G = p[1];// = 0; //Green
				//	B = p[2];// = 0; //Blue
				convertirRgbAY(p[0], p[1], p[2], Y);
				//#picture->data[0][y * picture->linesize[0] + x] = x + y + i * 2;
				picture->data[0][y * picture->linesize[0] + x] = Y;
			}
		}
		/* Cb and Cr */
		for (y = 0; y < c->height / 2; y++) {
			for (x = 0; x < c->width / 2; x++) {
				p = pixels + y * rowstride + x * n_channels;
				convertirRgbAUv(p[0], p[1], p[2], U, V);
				//#	picture->data[1][y * picture->linesize[1] + x] = 128 + y + i* 2;
				//#	picture->data[2][y * picture->linesize[2] + x] = 64 + x + i * 5;
				picture->data[1][y * picture->linesize[1] + x] = U;//= Cb;
				picture->data[2][y * picture->linesize[2] + x] = V;//= Cr;
			}
		}

		/* encode the image */
		out_size = avcodec_encode_video(c, outbuf, outbuf_size, picture);
		printf("encoding frame %3d (size=%5d)\n", i, out_size);
		fwrite(outbuf, 1, out_size, f);
	}

	/* get the delayed frames */
	for (; out_size; i++) {
		fflush(stdout);

		out_size = avcodec_encode_video(c, outbuf, outbuf_size, NULL);
		printf("write frame %3d (size=%5d)\n", i, out_size);
		fwrite(outbuf, 1, out_size, f);
	}

	/* add sequence end code to have a real mpeg file */
	outbuf[0] = 0x00;
	outbuf[1] = 0x00;
	outbuf[2] = 0x01;
	outbuf[3] = 0xb7;
	fwrite(outbuf, 1, 4, f);
	fclose(f);
	free(picture_buf);
	free(outbuf);

	avcodec_close(c);
	av_free(c);
	av_free(picture);
	printf("\n");
}

void imprimirPixeles(Gtk::Image* captura) {
	Glib::RefPtr < Gdk::Pixbuf > screenshot = captura->get_pixbuf();
	std::cout << "pixeles" << std::endl;
	int x = 10;
	int y = 10;
	int width, height, rowstride, n_channels;
	guchar *pixels, *p;

	n_channels = gdk_pixbuf_get_n_channels(screenshot->gobj());
	width = gdk_pixbuf_get_width(screenshot->gobj());
	height = gdk_pixbuf_get_height(screenshot->gobj());
	rowstride = gdk_pixbuf_get_rowstride(screenshot->gobj());

	pixels = gdk_pixbuf_get_pixels(screenshot->gobj());
	p = pixels + y * rowstride + x * n_channels;
	//	p[0] = 0; //Red
	//	p[1] = 0; //Green
	//	p[2] = 0; //Blue
	//	p[3] = 0;

	int R = p[0];// = 0; //Red
	int G = p[1];// = 0; //Green
	int B = p[2];// = 0; //Blue
	int U, V;

	int Y = (0.257 * R) + (0.504 * G) + (0.098 * B) + 16;
	int Cr = V = (0.439 * R) - (0.368 * G) - (0.071 * B) + 128;
	int Cb = U = -(0.148 * R) - (0.291 * G) + (0.439 * B) + 128;

	std::cout << "p0:" << (unsigned int) p[0] << std::endl;
	std::cout << "p1:" << (unsigned int) p[1] << std::endl;
	std::cout << "p2:" << (unsigned int) p[2] << std::endl;
	std::cout << "p3:" << (unsigned int) p[3] << std::endl;

	std::cout << "Y:" << (unsigned int) Y << std::endl;
	std::cout << "Cr:" << (unsigned int) Cr << std::endl;
	std::cout << "Cb:" << (unsigned int) Cb << std::endl;

	gdk_pixbuf_save(screenshot->gobj(), "./screenshot.jpg", "jpeg", NULL,
			"quality", "100", NULL);
}

void on_click_btGuardar(Gtk::Image* captura) {
	//	Glib::RefPtr < Gdk::Pixbuf > screenshot = captura->get_pixbuf();
	//	gdk_pixbuf_save(screenshot->gobj(), "./screenshot.jpg", "jpeg", NULL,
	//			"quality", "100", NULL);
	//
	encodeImagePixbuf("ejemplo.mpeg", captura);
	//imprimirPixeles(captura);
}

void on_click_btBorrar(Gtk::Image* captura) {
	captura->clear();
}

void on_click_btCapturar(Gtk::Image* captura) {
	GdkWindow *window_root;
	GdkPixbuf *screenshot;
	gint width, height;

	window_root = gdk_get_default_root_window();
	gdk_drawable_get_size(GDK_DRAWABLE(window_root), &width, &height);

	screenshot = gdk_pixbuf_get_from_drawable(NULL, window_root, NULL, 0, 0, 0,
			0, width, height);

	GdkPixbuf * pixbuf_scale = gdk_pixbuf_scale_simple(screenshot, 900, 600,
			GDK_INTERP_BILINEAR);

	gtk_image_set_from_pixbuf(captura->gobj(), pixbuf_scale);

	//gdk_pixbuf_save(pixbuf_scale , "./screenshot.jpg", "jpeg", NULL, "quality", "100", NULL);
	//captura->set("./screenshot.jpg");

	//	CapturadorPantalla * capturador = new CapturadorPantalla(captura);
	//	capturador->run();

}

int main(int arc, char **argv) {
	// Creo la GUI a partir del .glade
	Gtk::Main kit(arc, argv);
/*
	Glib::RefPtr < Gtk::Builder > builder;
	builder = Gtk::Builder::create_from_file("./screenshot.glade"); //Falta controlar error noexiste Archivo.

	// Maximizo ventana principal
	Gtk::Window* window = 0;
	builder->get_widget("window1", window);
	window->maximize();
	window->show();

	// Manejo la captura de pantalla.
	Gtk::Button * btCapturar;
	Gtk::Button * btGuardar;
	Gtk::Button * btBorrar;
	Gtk::Image * imgCaptura;

	builder->get_widget("btCapturar", btCapturar);
	builder->get_widget("btGuardar", btGuardar);
	builder->get_widget("btBorrar", btBorrar);
	builder->get_widget("imgCaptura", imgCaptura);

	btCapturar->signal_clicked().connect(
			sigc::bind<Gtk::Image*>(sigc::ptr_fun(&on_click_btCapturar),
					imgCaptura));

	btBorrar->signal_clicked().connect(
			sigc::bind<Gtk::Image*>(sigc::ptr_fun(&on_click_btBorrar),
					imgCaptura));

	btGuardar->signal_clicked().connect(
			sigc::bind<Gtk::Image*>(sigc::ptr_fun(&on_click_btGuardar),
					imgCaptura));
*/

	GdkWindow *window_root;
	GdkPixbuf *screenshot;
	gint width, height;
	Gtk::Image imgCaptura;

	window_root = gdk_get_default_root_window();
	gdk_drawable_get_size(GDK_DRAWABLE(window_root), &width, &height);
	screenshot = gdk_pixbuf_get_from_drawable(NULL, window_root, NULL, 0, 0, 0,	0, width, height);
	GdkPixbuf * pixbuf_scale = gdk_pixbuf_scale_simple(screenshot, 900, 600, GDK_INTERP_BILINEAR);
	gtk_image_set_from_pixbuf(imgCaptura.gobj(), pixbuf_scale);

	//gdk_pixbuf_save(pixbuf_scale , "./screenshot.jpg", "jpeg", NULL, "quality", "100", NULL);
	//captura->set("./screenshot.jpg");

	//	CapturadorPantalla * capturador = new CapturadorPantalla(captura);
	//	capturador->run();


	encodeImagePixbuf("ejemplo.mpeg", &imgCaptura);

	//av_register_all();
	//kit.run(*window);
	return 0;
}
