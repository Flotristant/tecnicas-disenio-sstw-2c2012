/*
 * Socket.cpp
 *
 *  Created on: 12/05/2011
 *      Author: pablo
 */

#include "TCPSocket.h"
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h>
#include <arpa/inet.h>

#include <string.h>

#include "exceptions/InitException.h"
#include "exceptions/BindException.h"
#include "exceptions/ConnectException.h"
#include "exceptions/ListenException.h"
#include "exceptions/AcceptException.h"
#include "exceptions/SocketException.h"

TCPSocket::TCPSocket() {
	this->descriptor = socket(AF_INET , SOCK_STREAM , 0);
	if (descriptor == -1) {
		throw InitException();
	}
	this->creado = true;
}

TCPSocket::~TCPSocket() {
	if (this->creado) {
		Close();
	}
}

void TCPSocket::Bind(int puerto) {
	sockaddr_in direccion_server;
	memset((char*)&(direccion_server) , 0 , sizeof(direccion_server));
	direccion_server.sin_family = AF_INET;
	direccion_server.sin_port = htons(puerto);
	direccion_server.sin_addr.s_addr = INADDR_ANY;
	if(bind(this->descriptor ,
			(struct sockaddr*)&direccion_server ,
			(socklen_t)sizeof(sockaddr)) < 0) {
		throw BindException();
	}
}

void TCPSocket::Listen(int max_cola_conexiones) {
	if (listen(this->descriptor , max_cola_conexiones) < 0)
		throw ListenException();
}

void TCPSocket::Connect(const std::string& ip , int puerto) {
	sockaddr_in direccion_server;
	memset((char*)&(direccion_server) , 0 , sizeof(direccion_server));
	direccion_server.sin_family = AF_INET;
	direccion_server.sin_port = htons(puerto);
	direccion_server.sin_addr.s_addr = inet_addr(ip.c_str());
	if (connect (this->descriptor, (struct sockaddr *)& direccion_server ,
			sizeof (direccion_server)) == -1) {
		throw ConnectException();
	}
}

void TCPSocket::ShutDown() {
	shutdown(this->descriptor , 2);
}

void TCPSocket::Close() {
	close(this->descriptor);
	this->creado = false;
}

TCPSocket* TCPSocket::Accept() {
	socklen_t tamanio = sizeof(struct sockaddr_in);
	int nuevo_cliente_descriptor;
	sockaddr_in dir_cliente;
	if ((nuevo_cliente_descriptor = accept(this->descriptor,
		(struct sockaddr *)&dir_cliente , &tamanio)) == -1) {
		throw AcceptException();
	}
	TCPSocket* sock_cliente_nuevo = new TCPSocket;
	sock_cliente_nuevo->descriptor = nuevo_cliente_descriptor;
	sock_cliente_nuevo->creado = true;
	return sock_cliente_nuevo;
}

void TCPSocket::Receive(char* data , int longitud) {
	int total_recibido = 0;
	while (total_recibido < longitud) {
		int cantidad = recv(this->descriptor , data + total_recibido , longitud - total_recibido , 0);
		if (cantidad <= 0)
			throw SocketException();
		total_recibido += cantidad;
	}
}

void TCPSocket::Send(char* data , int longitud) {
	int total_enviado = 0;
	while (total_enviado < longitud) {
		int cantidad = send(this->descriptor , data + total_enviado , longitud - total_enviado , 0);
		if (cantidad <= 0)
			throw SocketException();
		total_enviado += cantidad;
	}
}

/*
void TCPSocket::Receive(PaqueteTCP& paq) {
	char c;
	Receive(&c , sizeof(char));
	paq.SetCodigo(c);
	unsigned int long_args;
	Receive((char*)&long_args , sizeof(unsigned int));
	paq.SetLongitudArgumentos(long_args);
	char* args;
	if(long_args > 0) {
		args = new char[long_args]();
		Receive(args , long_args);
		paq.SetArgumentos(args);
	}
}

void TCPSocket::Send(PaqueteTCP& paq) {
	char cod = paq.GetCodigo();
	Send(&cod , sizeof(char));
	unsigned int long_args = paq.GetLongitudArgumentos();
	Send((char*)&long_args , sizeof(unsigned int));
	if( long_args > 0) {
		Send(paq.GetArgumentos() , long_args);
	}
}
*/
