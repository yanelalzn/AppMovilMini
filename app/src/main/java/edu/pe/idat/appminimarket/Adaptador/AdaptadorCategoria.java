package edu.pe.idat.appminimarket.Adaptador;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import edu.pe.idat.appminimarket.CategoriaListActivity;
import edu.pe.idat.appminimarket.Modelo.CategoriaCV;
import edu.pe.idat.appminimarket.R;

public class AdaptadorCategoria
        extends RecyclerView.Adapter <AdaptadorCategoria.ViewHolderDatos> {

    ArrayList<CategoriaCV> ItemsDato;

    public AdaptadorCategoria(ArrayList<CategoriaCV> itemsDato) {

        ItemsDato = itemsDato;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_file_categoria,null,false);

        return new AdaptadorCategoria.ViewHolderDatos(view);

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {

        holder.asignarDatos( ItemsDato.get(position));

        holder.setOnClickListener();

    }


    @Override
    public int getItemCount() {

        return ItemsDato.size();
    }



    public class ViewHolderDatos extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Variable del Contexto
        Context context;
        // Variable del CartView
        TextView dato_codigo;
        TextView dato_descripcion;
        ImageButton dato_imagen;

        public ViewHolderDatos(@NonNull View itemView) {

            super(itemView);

            context = itemView.getContext();

            dato_imagen = itemView.findViewById(R.id.imagenCategoriaLis);
            dato_codigo = itemView.findViewById(R.id.codigoCategoria);
            dato_descripcion = itemView.findViewById(R.id.descripcionCategoria);

        }

        void setOnClickListener(){
            dato_imagen.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Variable del CartView

            Intent intent = new Intent(context, CategoriaListActivity.class);

            intent.putExtra("codigo_categoria",dato_codigo.getText());
            intent.putExtra("dato_titulo",dato_descripcion.getText());

            context.startActivity(intent);
        }

        public void asignarDatos(CategoriaCV categoriaCV) {

          //  Bitmap obtener_imagen = get_imagen(categoriaCartView.getImagenproducto());

           // BitmapDrawable obtener_imagen = new BitmapDrawable(context.getResources(),
           //         BitmapFactory.decodeFile(categoriaCartView.getImagenproducto()));

            dato_descripcion.setText(categoriaCV.getDescripcion());
            dato_codigo.setText(String.valueOf(categoriaCV.getId_categoria()));

            Picasso.get()
                    .load(categoriaCV.getImagenproducto())
                    .resize(550, 550)
                    .centerCrop()
                    .into(dato_imagen);

//   .resize(200, 200)
            //dato_imagen.setImageDrawable(obtener_imagen);
            // dato_imagen.setImageBitmap(categoriaCartView.getImagenproducto());  //setImageResource(categoriaCartView.getImagenproducto());

        }

        private Bitmap get_descripcion(String url) {
            Bitmap bm = null;
            try {
                URL _url = new URL(url);
                URLConnection con = _url.openConnection();
                con.connect();
                InputStream is = con.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {

            }
            return bm;
        }
    }
}
