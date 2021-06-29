package edu.pe.idat.appminimarket.Adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.pe.idat.appminimarket.Modelo.ProductoCV;
import edu.pe.idat.appminimarket.ProductoDetalleActivity;
import edu.pe.idat.appminimarket.R;

public class AdaptadorProducto extends RecyclerView.Adapter <AdaptadorProducto.ViewHolderDatos> {

    ArrayList<ProductoCV> ItemsDato;

    public AdaptadorProducto(ArrayList<ProductoCV> itemsDato) {

        ItemsDato = itemsDato;
            }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_file_producto,null,false);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {

        holder.asignarDatos(ItemsDato.get(position));
        holder.setOnClickListener();

    }

    @Override
    public int getItemCount() {
        return ItemsDato.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Variable del Contexto
        Context context;

        TextView codcategoriaProd;
        TextView codproduct;
        TextView dato_desripcion;
        TextView precioproduc;
        ImageButton imagenproduct;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);

            context = itemView.getContext();

            imagenproduct = itemView.findViewById(R.id.imagenproduct);
            codcategoriaProd = itemView.findViewById(R.id.codcategoriaProd);
            codproduct = itemView.findViewById(R.id.codproduct);
            dato_desripcion = itemView.findViewById(R.id.descripcionproductoLis);
            precioproduc = itemView.findViewById(R.id.precioproduc);
        }


        public void setOnClickListener() {
            imagenproduct.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            // Variable del CartView

            Intent intent = new Intent(context, ProductoDetalleActivity.class);


            intent.putExtra("codigo_categoria", codcategoriaProd.getText());
            intent.putExtra("codigo_producto", codproduct.getText());


            context.startActivity(intent);

        }

        public void asignarDatos(ProductoCV pcv) {
            codcategoriaProd.setText(String.valueOf(pcv.getId_categoria()));
            codproduct.setText(String.valueOf(pcv.getId_producto()));
            dato_desripcion.setText(pcv.getDescripcion());
            precioproduc.setText(Double.toString(pcv.getPrecio()));


            if(pcv.getImagenproducto().isEmpty()){


            }else {
                Picasso.get()
                        .load(pcv.getImagenproducto())
                        .resize(100, 100)
                        .centerCrop()
                        .into(imagenproduct);

            }




        }



    }
}
