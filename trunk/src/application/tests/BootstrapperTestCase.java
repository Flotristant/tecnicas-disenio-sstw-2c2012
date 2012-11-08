package application.tests;

import model.ActionRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.picocontainer.MutablePicoContainer;

import application.Bootstrapper;
import application.tests.mocks.PicoContainerMock;

public class BootstrapperTestCase {
	private PicoContainerMock container;

	@Before
	public void setUp() throws Exception {
		this.container = new PicoContainerMock();
	}
	
	@Test
	public void testShouldCreateContainerWhenRunning(){
		TestableBootstrapper bootstrapper = this.createBootstrapper();
		
		bootstrapper.setContainerToReturn(this.container);
		
		Assert.assertNull(bootstrapper.getContainer());
		
		bootstrapper.run();
		
		Assert.assertNotNull(bootstrapper.getContainer());
		junit.framework.Assert.assertSame(this.container, bootstrapper.getContainer());
	}
	
	@Test
	public void testShouldConfigureMappings(){
		TestableBootstrapper bootstrapper = this.createBootstrapper();
		bootstrapper.setContainerToReturn(this.container);
		
		Assert.assertEquals(0, this.container.getMappings().size());
		
		bootstrapper.run();
		
		Assert.assertEquals(11, this.container.getMappings().size());
		Assert.assertSame(this.container, this.container.getMappings().get(MutablePicoContainer.class));
		
		// despues sacar
		Assert.assertNotNull(this.container.getComponent(ActionRule.class + "ActionAltaAlumno"));
	}
	
	private TestableBootstrapper createBootstrapper(){
		return new TestableBootstrapper();
	}
	
	private class TestableBootstrapper extends Bootstrapper	{
		private MutablePicoContainer containerToReturn;
		
		public void setContainerToReturn(MutablePicoContainer c){
			this.containerToReturn = c;
		}
		
		@Override
		public MutablePicoContainer createContainer(){
			return this.containerToReturn;
		};
	}
}
