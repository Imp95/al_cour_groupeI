package groupei.al.blablacar;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import org.json.JSONObject;

public class RequestHandler {
    private static RequestHandler instance;
    public static synchronized RequestHandler getInstance(Context ctx){
        if (instance==null)
            instance = new RequestHandler(ctx);
        return instance;
    }
    private RequestQueue requestQueue;
    private Cache cache;
    private Network network;
    private Context ctx;


    private RequestHandler(Context ctx){
        this.ctx = ctx;
        cache = new DiskBasedCache(ctx.getCacheDir(), 1024 * 1024); // 1MB cap
        network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();

    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
