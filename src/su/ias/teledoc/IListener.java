package su.ias.teledoc;

/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 02.06.2014
 * Time: 12:54
 */
public interface IListener {

    public void responseCompleteHandler(Integer serviceType, Integer currStep, String jsonFromApi);
    public void responseErrorHandler(Integer serviceType, Integer currStep, String errorStr);


   /* //if cookies are expired
    public void authorizeUser();
*/

}
