package hermescuitinorodriguez.laboratorio.hermescomunicador;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GridViewImageAdapter extends BaseAdapter {

	private Activity _activity;
	private List<Integer> listaIdImagenes = new ArrayList<Integer>();
	private int imageWidth;
	private List<String> audios = new ArrayList<String>();

	public GridViewImageAdapter(Activity activity, List<Integer> listaIdImagenes,
			int imageWidth, List<String> listaNombreImagenes) {
		this._activity = activity;
		this.listaIdImagenes = listaIdImagenes;
		this.imageWidth = imageWidth;
		this.audios = listaNombreImagenes;
	}

	@Override
	public int getCount() {
		return this.listaIdImagenes.size();
	}

	@Override
	public Object getItem(int position) {
		return this.listaIdImagenes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(_activity);
		} else {
			imageView = (ImageView) convertView;
		}


		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageWidth));
		//imageView.setImageDrawable(_activity.getResources().getDrawable(this.listaIdImagenes.get(position)));
		((AlumnoActivity) _activity).loadBitmap(this.listaIdImagenes.get(position), imageView);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int soundId = _activity.getResources().getIdentifier(audios.get(position), "raw", _activity.getPackageName());
				MediaPlayer mediaPlayer = MediaPlayer.create(_activity, soundId);
				mediaPlayer.start();
			}
		});
	
		return imageView;
	}

}
