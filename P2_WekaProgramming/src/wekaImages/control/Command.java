/**
@file Comando.java

Descripci�n Archivo en el que se define la clase que define la interfaz
			que luego los comandos concretos deberan implementar
*/
package wekaImages.control;
import java.awt.event.*;

/**
 * Abstract class responsible for providing the functionality of the PD Command.
The subclasses of the command are responsible for implementing all the functionality 
of the application.
 *
 * @author Jose Francisco
 * @version 1.0
 * @see java.awt.event.ActionListener
 * @see java.awt.event.ItemListener
 */
public abstract class Command implements ActionListener{
	/**
	 * Run the command
	 */
	public abstract void runCommand();
	
	

	/**
	 * {@inheritDoc}
	 */
	public void actionPerformed(ActionEvent cmd){
		Executor ex = new Executor();
		ex.start();
	}
	
	
	
	private class Executor extends Thread {		
		@Override
		public void run(){
			runCommand();
		}
		
	}
	
}
