package mx.edu.utng.cookingtime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.recetaView> implements Filterable {

    private List<Receta> recetaList = new ArrayList<>();
    private Context context;

    private ArrayList<Receta> recetaArrayList;


    private IAxiliarReceta iAxiliarReceta;


    public RecetaAdapter(IAxiliarReceta iAxiliarReceta, ArrayList<Receta> recetaList) {
        this.iAxiliarReceta = iAxiliarReceta;
        this.recetaList = recetaList;
        this.recetaArrayList = recetaList;
    }

    @NonNull
    @Override
    public recetaView onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mostrar, viewGroup, false);
        return new recetaView(view);

    }

    @Override
    public void onBindViewHolder(recetaView recetaView, int i) {
        Receta receta = recetaList.get(i);
        recetaView.txtcaloriasMostrar.setText(String.valueOf(receta.getCalorias()));
        recetaView.txtnombreMostrar.setText(receta.getNombre());
        recetaView.txtingredientesMostrar.setText(receta.getIngredientes());
        recetaView.txtcategoriaMostrar.setText(receta.getCategoria());
        recetaView.btnEditar.setOnClickListener(new eventoEditar(receta));
        recetaView.btnEliminar.setOnClickListener(new eventoEliminar(receta));
    }


    @Override
    public int getItemCount() {
        return recetaList.size();
    }


    public void agregarReceta(Receta receta) {
        recetaList.add(receta);
        this.notifyDataSetChanged();
    }

    public void eliminarReceta(Receta receta) {
        recetaList.remove(receta);
        this.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String palabra = constraint.toString();

                if (palabra.isEmpty()) {
                    recetaList = recetaArrayList;
                } else {
                    ArrayList<Receta> filtrarLista = new ArrayList<>();
                    for (Receta receta : recetaArrayList) {
                        if (receta.getNombre().toLowerCase().contains(constraint)) {
                            filtrarLista.add(receta);
                        }
                    }
                    recetaList = filtrarLista;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = recetaList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                recetaList = (ArrayList<Receta>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    class eventoEditar implements View.OnClickListener {

        private Receta receta;

        public eventoEditar(Receta receta) {
            this.receta= receta;
        }

        @Override
        public void onClick(View v) {
            iAxiliarReceta.OpcionEditar(receta);
        }
    }


    class eventoEliminar implements View.OnClickListener {
        private Receta receta;

        public eventoEliminar(Receta receta) {
            this.receta = receta;
        }

        @Override
        public void onClick(View v) {
            iAxiliarReceta.OpcionEliminar(receta);
        }
    }


    public class recetaView extends RecyclerView.ViewHolder {
        private TextView txtcaloriasMostrar, txtnombreMostrar, txtingredientesMostrar, txtcategoriaMostrar;
        private Button btnEditar, btnEliminar;

        public recetaView(@NonNull View itemView) {
            super(itemView);
            txtcaloriasMostrar = itemView.findViewById(R.id.txtCaloriasMostrar);
            txtnombreMostrar = itemView.findViewById(R.id.txtNombreMostrar);
            txtingredientesMostrar = itemView.findViewById(R.id.txtIngredientesMostrar);
            txtcategoriaMostrar = itemView.findViewById(R.id.txtCategoriaMostrar);
            btnEditar = itemView.findViewById(R.id.btnEliminar);
            btnEliminar = itemView.findViewById(R.id.btnEditar);
        }
    }

}
