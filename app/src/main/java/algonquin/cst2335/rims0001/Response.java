package algonquin.cst2335.rims0001;

import androidx.annotation.Nullable;

import com.android.volley.Cache;
import com.android.volley.VolleyError;

public class Response<T> {
    public Response(T result, T cahceEntry) {

    }

    public interface Listener<T>{
        void onResponse(T response);
    }

    public  interface ErrorListener {
        void onErrorResponse(VolleyError error);
    }

    public static <T> Response success(@Nullable T result, @Nullable Cache.Entry cahceEntry){
        return new Response<>(result, cahceEntry);
    }
}
