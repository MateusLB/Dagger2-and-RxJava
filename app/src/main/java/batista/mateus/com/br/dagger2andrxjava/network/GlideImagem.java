package batista.mateus.com.br.dagger2andrxjava.network;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class GlideImagem {

    private Context context;

    public GlideImagem(Context context) {
        this.context = context;
    }

    public void downloadImg(String url, ImageView imageView){
        Glide.with(context)
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }
}
