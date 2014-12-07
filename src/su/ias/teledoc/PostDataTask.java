package su.ias.teledoc;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import su.ias.teledoc.activities.AbstractActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 * User: n.senchurin
 * Date: 11.07.2014
 * Time: 12:36
 */

public class PostDataTask extends AsyncTask<Object, Void, Boolean> {

    public static final int STEP_1 = 1;   // get sms
    public static final int STEP_2 = 2;   // check sms
    public static final int STEP_3 = 3;   // send form data

    private String errorCode;
    private String errorMessage;

    private IListener listener;

    private int serviceType;
    private int currStep;

    private String dataFromServer;

    private static final String OK = "ok";
    private static final String ERROR = "error";


    private static final String SERVER_URL_1="http://***/SmsService.svc/SendCode";
    private static final String SERVER_URL_2="http://***/SmsService.svc/CheckCode";
    private static final String SERVER_URL_3="http://***/RequestService.svc/CreateEcpRequest";


    public PostDataTask(IListener listener) {
        this.listener = listener;
    }


    @Override
    protected Boolean doInBackground(Object... params) {

        boolean result = true;

        serviceType = (Integer) params[0];
        currStep = (Integer) params[1];
        String jsonToPostStr = (String) params[2];
           Log.i("@","json to post "+jsonToPostStr);
        DefaultHttpClient client = new DefaultHttpClient();
        client.getParams().setBooleanParameter("http.protocol.expect-continue", false);

        String url = getUrl(currStep,serviceType);
        Log.i("@","url to post "+ url);
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-Type", "application/json; charset=utf-8");
        httppost.setHeader("Accept", "application/json");

        try {

            StringEntity stringEntity = new StringEntity(jsonToPostStr, "UTF-8");

            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json; charset=utf-8"));
            httppost.setEntity(stringEntity);

            HttpResponse response = client.execute(httppost);

            BufferedReader ins = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder("");
            dataFromServer = "";
            while ((dataFromServer = ins.readLine()) != null) {
                sb.append(dataFromServer);
            }
            dataFromServer = sb.toString();

            Log.i("@","encodedStr " + dataFromServer);

            JSONObject jsonObject = new JSONObject(dataFromServer);

//            String status = jsonObject.getString("status");
//            result = status.equals(OK); // else ERROR

        } catch (UnsupportedEncodingException e) {
            result = false;
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            result = false;
            e.printStackTrace();
        } catch (IOException e) {
            result = false;
            e.printStackTrace();
        } catch (JSONException e) {
            result = false;
            e.printStackTrace();
        }

        return result;
    }




    @Override
    protected void onPostExecute(Boolean result) {

        if (result) {
            Log.w("@", "success "+ dataFromServer);
            listener.responseCompleteHandler(serviceType, currStep, dataFromServer);
        } else {
            Log.e("@", "error "+ dataFromServer);
            listener.responseErrorHandler(serviceType, currStep, dataFromServer);
        }
    }



    private String getUrl(int step, int serviceType ) {
        String url = "";

        if(step == STEP_1) return "http://***/SmsService.svc/SendCode";
        if(step == STEP_2) return "http://***/SmsService.svc/CheckCode";

        if(step == STEP_3){
            if(serviceType == AbstractActivity.ELECTRONIC_SIGNATURE_TYPE)
                return "http://***/RequestService.svc/CreateEcpRequest";

            if(serviceType == AbstractActivity.BANK_GUARANTEE_TYPE)
                return "http://***/RequestService.svc/CreateBgRequest";

            if(serviceType == AbstractActivity.ELECTRONIC_DOCUMENTATION_TYPE)
                return "http://***/RequestService.svc/CreateEdoRequest";

            if(serviceType == AbstractActivity.PAYMENT_SERVICE_TYPE)
                return "http://***/RequestService.svc/CreateMposRequest";

            if(serviceType == AbstractActivity.AGENT_TYPE)
                return "http://***/RequestService.svc/CreateAgentRequest";
        }

        return url;
    }



}
