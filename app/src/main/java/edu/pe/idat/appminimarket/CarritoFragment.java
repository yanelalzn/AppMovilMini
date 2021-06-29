package edu.pe.idat.appminimarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import edu.pe.idat.appminimarket.Modelo.CarritoDetalle;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarritoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarritoFragment extends Fragment {

    // Varialbles globales
    ArrayList<CarritoDetalle> ItemsDato;
    CarritoDetalle ItemDato;
    RecyclerView recycler;
    View v;
    //** declarar la cola de peticiones
    private RequestQueue colaPeticiones;

    Button botonRealizarCompra;

    TextView totalPagarCarritoMsg, mensajeCarrito;

    private Button salir;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CarritoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Carrilto2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarritoFragment newInstance(String param1, String param2) {
        CarritoFragment fragment = new CarritoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_carrito, container, false);

        //Amarrar el reclicer del carteview
        recycler = v.findViewById(R.id.recyclerElementosCarritoVenta);
        recycler.setLayoutManager(
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        botonRealizarCompra = v.findViewById(R.id.realizarcompraCarritoVenta);
        totalPagarCarritoMsg = v.findViewById(R.id.totalpagarCarritoVenta);
        mensajeCarrito = v.findViewById(R.id.mensajeCarritoVenta);

        // Crear array de items categoria
        ItemsDato = new ArrayList<CarritoDetalle>();

        //** Creamos la cola de peticiones
        colaPeticiones = Volley.newRequestQueue(getActivity());


        // Cargar la lista del carrito de venta
        CargarListaCarritoVentaVolley();

        // Boton para activar activity de compra
        botonRealizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Verificar si tenemos al menos un articulo en carrito para realizar la compra
                if (ItemsDato.size() > 0) {


                    Intent intent = new Intent(getContext(), ActivityReClienteEnvio.class);

                    intent.putExtra("total_pagar",totalPagarCarritoMsg.getText());

                    startActivity(intent);

                }else
                {
                    mensajeCarrito.setText("El carrito no cuenta con producto");
                }
            }
        });



        return v;
    }

    private void CargarListaCarritoVentaVolley() {
    }


}