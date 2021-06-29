package edu.pe.idat.appminimarket.Adaptador;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.pe.idat.appminimarket.ActivityEliminacventa;
import edu.pe.idat.appminimarket.Modelo.CarritoDetalle;
import edu.pe.idat.appminimarket.R;

public class AdaptadorDetalleCVenta extends RecyclerView.Adapter <AdaptadorDetalleCVenta.ViewHolderDatos> {

    ArrayList<CarritoDetalle> ItemsDato;

    public AdaptadorDetalleCVenta(ArrayList<CarritoDetalle> itemsDato) {

        ItemsDato = itemsDato;
    }

    @NonNull
    @Override
    public AdaptadorDetalleCVenta.ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_file_detallecventa,null,false);



        return new AdaptadorDetalleCVenta.ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorDetalleCVenta.ViewHolderDatos holder, int position) {

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
        // Variable del CartView
        TextView dato_idClienteCarrito;
        TextView dato_idVentaCarrito;
        TextView dato_idVentaDetalleCarrito;
        TextView dato_codigoproducto;
        TextView dato_descripcionproducto;
        TextView dato_cantidadproducto;
        TextView dato_precioproducto;
        TextView dato_totalproducto;

        ImageButton dato_imagen;
        Button dato_eliminarcarrito;

        public ViewHolderDatos(@NonNull View itemView ){
            super(itemView);

            context = itemView.getContext();

            dato_idVentaCarrito= itemView.findViewById(R.id.idVentaCarrito);
            dato_idVentaDetalleCarrito = itemView.findViewById(R.id.idVentaDetalleCarrito);
            dato_idClienteCarrito = itemView.findViewById(R.id.idClienteCarrito);
            dato_imagen = itemView.findViewById(R.id.imagenproductoCarrito);
            dato_codigoproducto = itemView.findViewById(R.id.codigoproductoCarrito);
            dato_descripcionproducto = itemView.findViewById(R.id.descripcionproductoCarrito);

            dato_cantidadproducto = itemView.findViewById(R.id.cantidadproductoCarrito);
            dato_precioproducto = itemView.findViewById(R.id.precioproductoCarrito);
            dato_totalproducto = itemView.findViewById(R.id.totalproductoCarrito);

            dato_eliminarcarrito = itemView.findViewById(R.id.eliminardetalleCarrito);
        }

        void setOnClickListener(){

            dato_eliminarcarrito.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Variable del CartView
            Intent intent = new Intent(context, ActivityEliminacventa.class);

            intent.putExtra("codigo_carritoventa",dato_idVentaCarrito.getText().toString());
            intent.putExtra("codigo_carritoventadetalle",dato_idVentaDetalleCarrito.getText().toString());

            context.startActivity(intent);

        }



        public void asignarDatos(CarritoDetalle carritoVenta) {


            dato_idVentaCarrito.setText(String.valueOf(carritoVenta.getId_carritoven()));
            dato_idVentaDetalleCarrito.setText(String.valueOf(carritoVenta.getId_carritovendeta()));
            dato_codigoproducto.setText(String.valueOf(carritoVenta.getId_producto()));
            dato_descripcionproducto.setText(carritoVenta.getNombreproducto());

            dato_cantidadproducto.setText("Cantidad : " + String.valueOf(carritoVenta.getCantidad_detalle()));
            dato_precioproducto.setText("Precio : S/. " + String.valueOf(carritoVenta.getPrecio_detalle()));
            dato_totalproducto.setText("Total : S/. " + String.valueOf(carritoVenta.getTotal_detalle()));

            Picasso.get()
                    .load(carritoVenta.getImagen())
                    .resize(200, 200)
                    .centerCrop()
                    .into(dato_imagen);

        }


    }
}
