package hermescuitinorodriguez.laboratorio.hermescomunicador;

import android.app.Activity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GridViewImageAdapter extends BaseAdapter {

    private String nombre;
    private String apellido;
    private String categoria;
    private String contenido;
	private Activity _activity;
	private List<Integer> listaIdImagenes = new ArrayList<Integer>();
	private int imageWidth;
	private List<String> audios = new ArrayList<String>();
	public String tag;
	public Boolean modoEdicion;
	public Alumno alumno;
	public ArrayList<String> listaPictogramaAlumno;
	public int numeroFragment;

	public GridViewImageAdapter(Activity activity, List<Integer> listaIdImagenes, Alumno alumno,
			int imageWidth, List<String> listaNombreImagenes, Boolean modo, ArrayList<String> listaPictogramaAlumno, int numeroFragment) {
		this._activity = activity;
		this.listaIdImagenes = listaIdImagenes;
		this.imageWidth = imageWidth;
		this.audios = listaNombreImagenes;
        this.nombre=alumno.getNombre();
        this.apellido=alumno.getApellido();
		this.modoEdicion = modo;
		this.alumno = alumno;
		this.listaPictogramaAlumno=listaPictogramaAlumno!=null ? listaPictogramaAlumno : new ArrayList<String>();
		this.numeroFragment = numeroFragment;
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

	public void remove(int position) {
		this.listaIdImagenes.remove(position);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ImageView imageView;
		imageView = new ImageView(_activity);
		/*if (convertView == null) {
			imageView = new ImageView(_activity);
		} else {
			imageView = (ImageView) convertView;
		}*/


		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new GridView.LayoutParams(imageWidth, imageWidth));
		((AlumnoActivity) _activity).loadBitmap(this.listaIdImagenes.get(position), imageView);

		int soundId = _activity.getResources().getIdentifier(audios.get(position), "raw", _activity.getPackageName());
		String nombreContenido = _activity.getResources().getResourceEntryName(soundId);
		if (modoEdicion && numeroFragment != 4 && listaPictogramaAlumno.contains(nombreContenido)) {
			imageView.setPadding(20, 20, 20, 20);
			imageView.setBackgroundColor(Color.argb(255,0,102,153));
		}

		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("entra a onClickListener de imageView");
				if (!modoEdicion) {
					int soundId = _activity.getResources().getIdentifier(audios.get(position), "raw", _activity.getPackageName());
					MediaPlayer mediaPlayer = MediaPlayer.create(_activity, soundId);
					mediaPlayer.start();

					String nombreContenido = _activity.getResources().getResourceEntryName(soundId);
					if (!nombreContenido.equals("si") && !nombreContenido.equals("no")) {
						Database db = new Database(_activity);
						String categoria = db.getCategoria(nombreContenido);
						NotificacionDTO notiDTO = new NotificacionDTO(alumno.getApellido(), alumno.getNombre(), categoria, "Cedica", nombreContenido);
						List<NotificacionDTO> lista = new ArrayList<NotificacionDTO>();
						lista.add(notiDTO);
						new SendNotificationTask().execute(lista, _activity.getApplicationContext());
					}
				} else {
					if (numeroFragment != 4) {
						Database db = new Database(_activity);
						int soundId = _activity.getResources().getIdentifier(audios.get(position), "raw", _activity.getPackageName());
						String nombreContenido = _activity.getResources().getResourceEntryName(soundId);
						if (listaPictogramaAlumno.contains(nombreContenido)) {
							listaPictogramaAlumno.remove(nombreContenido);
							db.borrarPictogramaAlumno(alumno.getId(), nombreContenido);
							imageView.setPadding(0, 0, 0, 0);
						} else {
							listaPictogramaAlumno.add(nombreContenido);
							db.cargarPictogramaAlumno(alumno.getId(), nombreContenido);
							imageView.setPadding(20, 20, 20, 20);
							imageView.setBackgroundColor(Color.argb(255, 0, 102, 153));
							notifyDataSetChanged();
						}
						((AlumnoActivity) _activity).actualizarFragmento(4);
					}


				}

			}
		});

		imageView.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View view) {
				if (modoEdicion && numeroFragment == 4) {
					int soundId = _activity.getResources().getIdentifier(audios.get(position), "raw", _activity.getPackageName());
					String nombreContenido = _activity.getResources().getResourceEntryName(soundId);
					if (!nombreContenido.equals("si") && !nombreContenido.equals("no")) {
						Database db = new Database(_activity);
						listaPictogramaAlumno.remove(nombreContenido);
						db.borrarPictogramaAlumno(alumno.getId(), nombreContenido);
						imageView.setPadding(0, 0, 0, 0);
						remove(position);
						notifyDataSetChanged();
						((AlumnoActivity) _activity).actualizarFragmento(3);
					}



				}
				return true;
			}
		});
	
		return imageView;
	}

}
