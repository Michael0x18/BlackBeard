package server.net;

/**
 * 
 * @author Michael Ferolito
 * @deprecated since version 2. The ExecutionEvent and related classes were
 *             never Thread safe, so they were bound to be replace. They are
 *             kept for backwards compatibility with earlier revisions of the
 *             Server and Clients that use ExecutionEvents. This class provides
 *             an interface, a plug-in of sorts, that allows classes outside the
 *             backend classes to speak to the Server and related classes. The
 *             lack of Thread scheduling caused this and related methods to
 *             throw generous quantities of concurrentModificationExceptions.
 *             They have been replaced with CAS model classes.
 *
 */
public interface ExecutionListener {

	public void execute(ExecutionEvent e);

}
