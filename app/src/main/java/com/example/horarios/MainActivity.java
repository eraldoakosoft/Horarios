package com.example.horarios;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.horarios.help.Horario;
import com.example.horarios.help.HorarioDia;
import com.example.horarios.help.MaskEditUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    public EditText  hEntrada, hSaidaCafe, hVoltaCafe, hSairAlmoco, hVoltaAlmoco, hSaida;
    public CheckBox checkBoxhEntrada, checkBoxhSaidaCafe, checkBoxhVoltaCafe, checkBoxhSairAlmoco, checkBoxhVoltaAlmoco, checkBoxhSaida;
    public EditText phEntrada, phSaidaCafe, phVoltaCafe, phSairAlmoco, phVoltaAlmoco, phSaida;
    private Handler handler = new Handler();
    private Runnable runnable;
    private TextView horaPrincipal, data, saldo, atrasoOuExtaras;
    private Button btnRegistrar;
    private String hora;
    private HorarioDia horarioDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBoxhEntrada = findViewById(R.id.checkboxHorarioEntar);
        checkBoxhSaidaCafe = findViewById(R.id.checkboxHorarioSairCafe);
        checkBoxhVoltaCafe = findViewById(R.id.checkboxHorarioVoltaCafe);
        checkBoxhSairAlmoco = findViewById(R.id.checkboxHorarioSaidaAlmoco);
        checkBoxhVoltaAlmoco = findViewById(R.id.checkboxHorarioVoltaAlmoco);
        checkBoxhSaida = findViewById(R.id.checkboxHorarioSair);
        horaPrincipal = findViewById(R.id.horaPrincipal);
        data = findViewById(R.id.data);
        saldo = findViewById(R.id.texViewSaldo);
        atrasoOuExtaras = findViewById(R.id.textViewAtrasoOuExtra);
        btnRegistrar = findViewById(R.id.btnRegistar);

        //Previsto
        phEntrada = findViewById(R.id.pHorarioEntar);
        phSaidaCafe = findViewById(R.id.pHorarioSairCafe);
        phVoltaCafe = findViewById(R.id.pHorarioVoltaCafe);
        phSairAlmoco = findViewById(R.id.pHorarioSaidaAlmoco);
        phVoltaAlmoco = findViewById(R.id.pHorarioVoltaAlmoco);
        phSaida = findViewById(R.id.pHorarioSair);
        /*
        phEntrada.addTextChangedListener(MaskEditUtil.mask(phEntrada, MaskEditUtil.FORMAT_HOUR));
        phSaidaCafe.addTextChangedListener(MaskEditUtil.mask(phSaidaCafe, MaskEditUtil.FORMAT_HOUR));
        phVoltaCafe.addTextChangedListener(MaskEditUtil.mask(phVoltaCafe, MaskEditUtil.FORMAT_HOUR));
        phSairAlmoco.addTextChangedListener(MaskEditUtil.mask(phSairAlmoco, MaskEditUtil.FORMAT_HOUR));
        phVoltaAlmoco.addTextChangedListener(MaskEditUtil.mask(phVoltaAlmoco, MaskEditUtil.FORMAT_HOUR));
        phSaida.addTextChangedListener(MaskEditUtil.mask(phSaida, MaskEditUtil.FORMAT_HOUR));
         */

        //Real
        hEntrada = findViewById(R.id.horarioEntar);
        hSaidaCafe = findViewById(R.id.horarioSairCafe);
        hVoltaCafe = findViewById(R.id.horarioVoltaCafe);
        hSairAlmoco = findViewById(R.id.horarioSaidaAlmoco);
        hVoltaAlmoco = findViewById(R.id.horarioVoltaAlmoco);
        hSaida = findViewById(R.id.horarioSair);
        atualizarHora();

        //MOSTRAR A DATA ATUAL NA TELA PRINCIPAL
        data.setText(dataAtual());

        //MOSTAR OS HORARIOS PADRÃO
        formatarHorariosPadrao();


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox();
                //calcularPrevisto();
            }
        });


    }

    public String dataAtual(){
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        String dataformatada = formataData.format(data);
        return dataformatada;
    }

    public void atualizarHora(){
        final Calendar calendar = Calendar.getInstance();

        runnable = new Runnable() {
            @Override
            public void run() {
                calendar.setTimeInMillis(System.currentTimeMillis());

                String horasFormatado = String.format("%02d",calendar.get(Calendar.HOUR_OF_DAY));
                String minutosFormatado = String.format("%02d",calendar.get(Calendar.MINUTE));
                String segundosFormatado = String.format("%02d",calendar.get(Calendar.SECOND));
                hora = horasFormatado + ":" + minutosFormatado + ":" + segundosFormatado;

                horaPrincipal.setText(hora);

                long agora = SystemClock.uptimeMillis();
                long proximo = agora + (1000 - (agora%1000));

                handler.postAtTime(runnable, proximo);

            }
        };
        runnable.run();

    }


    /*public void registar(){

        try {


            //Criar banco de dados
            SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);

            //Criar tabela
            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS horario (id_horario_data VARCHAR PRIMARY KEY NOT NULL, " +
                    "hora_entrada VARCHAR, " +
                    "hora_saida_cafe VARCHAR, " +
                    "hora_volta_cafe VARCHAR," +
                    "hora_saida_almoco VARCHAR," +
                    "hora_volta_almoco VARCHAR," +
                    "hora_saida VARCHAR)");

            //Inserir Dados
            bancoDados.execSQL("INSERT INTO horario(id_horario_data," +
                     "hora_entrada," +
                     "hora_saida_cafe, " +
                     "hora_volta_cafe, " +
                     "hora_saida_almoco, " +
                     "hora_volta_almoco, " +
                     "hora_saida) VALUES ()");
        }catch (Exception e){
            Toast.makeText(MainActivity.this, "Erro no Banco",Toast.LENGTH_SHORT).show();
        }
    }

     */


    public void checkBox(){
        if ( checkBoxhEntrada.isChecked() ){
            hEntrada.setText(hora);
            passo1();
        }else if ( checkBoxhSaidaCafe.isChecked() ){
            hSaidaCafe.setText(hora);
            passo2();
        }else if ( checkBoxhVoltaCafe.isChecked() ){
            hVoltaCafe.setText(hora);
            passo3();
        }else if ( checkBoxhSairAlmoco.isChecked() ){
            hSairAlmoco.setText(hora);
            passo4();
        }else if ( checkBoxhVoltaAlmoco.isChecked() ){
            hVoltaAlmoco.setText(hora);
            passo5();
        }else if ( checkBoxhSaida.isChecked() ){
            hSaida.setText(hora);
            calcularSaldoDeHoras();
        }

    }




/*    public void calcularPrevisto(){
        String[] textoSeparado = hEntrada.getText().toString().split(":");
        int h = Integer.parseInt(textoSeparado[0]);
        int m = Integer.parseInt(textoSeparado[1]);
        int s = Integer.parseInt(textoSeparado[2]);
        System.out.println("Hora : " + h);
        System.out.println("Minuto : " + m);
        System.out.println("Segundo : " + s);

    }

 */

    //FORMATAR OS HORARIOS PADRÃO
    public void formatarHorariosPadrao(){
        phEntrada.setText("07:00:00");
        phSaidaCafe.setText("08:00:00");
        phVoltaCafe.setText("08:15:00");
        phSairAlmoco.setText("12:50:00");
        phVoltaAlmoco.setText("14:20:00");
        phSaida.setText("17:00:00");
    }

    public void atualizarHorairoPrevisto(){
        phEntrada.setText(hEntrada.getText());
    }

    //ESSA FUNÇÃO VAI CALCULAR O HORARIO PREVISTO DE SAIDA
    public void passo1(){
        phSaidaCafe.setText("00:00:00");
        phVoltaCafe.setText("00:00:00");
        phSairAlmoco.setText("12:50:00");
        phVoltaAlmoco.setText("14:20:00");

        Horario horario = new Horario();
        Horario hPrevistoSaida = new Horario();

        String[] textoSeparado = hEntrada.getText().toString().split(":");
        horario.setHoras((Integer.parseInt(textoSeparado[0]))*3600);
        horario.setMinutos((Integer.parseInt(textoSeparado[1]))*60);
        horario.setSegundos(Integer.parseInt(textoSeparado[2]));
        horario.setTotalSegundos(horario.getHoras()+horario.getMinutos()+horario.getSegundos());
        hPrevistoSaida.setTotalSegundos(horario.getTotalSegundos()+30600+5400);
        hPrevistoSaida.setHoras(hPrevistoSaida.getTotalSegundos()/3600);
        hPrevistoSaida.setMinutos((hPrevistoSaida.getTotalSegundos()%3600)/60);
        hPrevistoSaida.setSegundos(((hPrevistoSaida.getTotalSegundos()%3600)%60));
        String horaSaida = formatarHorario(hPrevistoSaida.getHoras(), hPrevistoSaida.getMinutos(), hPrevistoSaida.getSegundos());
        phSaida.setText(horaSaida);
    }

    //ESSA FUNÇÃO CAlCULA A HORA DE VOLTAR DO CAFÉ E CALCULAR A HORA DE SAIDA JÁ COM O TEMPO DE CAFÉ CONTABILIZADO
    public void passo2(){
        phSaidaCafe.setText(hSaidaCafe.getText());
        Horario horarioEntrada;
        Horario hVoltaC;
        Horario hPrevistoSaida = new Horario();
        horarioEntrada = configurarHorario(hEntrada.getText().toString());
        hVoltaC = configurarHorario(hSaidaCafe.getText().toString());
        hVoltaC.setTotalSegundos(hVoltaC.getTotalSegundos()+900);
        hVoltaC.setHoras(hVoltaC.getTotalSegundos()/3600);
        hVoltaC.setMinutos((hVoltaC.getTotalSegundos()%3600)/60);
        hVoltaC.setSegundos(((hVoltaC.getTotalSegundos()%3600)%60));
        phVoltaCafe.setText(formatarHorario(hVoltaC.getHoras(),hVoltaC.getMinutos(),hVoltaC.getSegundos()));

        hPrevistoSaida.setTotalSegundos(horarioEntrada.getTotalSegundos()+30600+5400+900);
        hPrevistoSaida.setHoras(hPrevistoSaida.getTotalSegundos()/3600);
        hPrevistoSaida.setMinutos((hPrevistoSaida.getTotalSegundos()%3600)/60);
        hPrevistoSaida.setSegundos(((hPrevistoSaida.getTotalSegundos()%3600)%60));
        String horaSaida = formatarHorario(hPrevistoSaida.getHoras(), hPrevistoSaida.getMinutos(), hPrevistoSaida.getSegundos());
        phSaida.setText(horaSaida);
    }

    //ESSA FUNÇÃO CALCULA A HORA DE SAIDA DESCONTADO O TEMPO GASTO COM O CAFE
    public void passo3(){
        phSaidaCafe.setText(hSaidaCafe.getText());
        Horario horarioEntrada;
        Horario hVoltaC;
        Horario hSaidaC;
        Horario hPrevistoSaida = new Horario();

        hSaidaC = configurarHorario(hSaidaCafe.getText().toString());
        hVoltaC = configurarHorario(hVoltaCafe.getText().toString());
        horarioEntrada = configurarHorario(hEntrada.getText().toString());
        horarioEntrada.setTotalSegundos(horarioEntrada.getTotalSegundos()+(hVoltaC.getTotalSegundos()-hSaidaC.getTotalSegundos()));

        hPrevistoSaida.setTotalSegundos(horarioEntrada.getTotalSegundos()+30600+5400);
        hPrevistoSaida.setHoras(hPrevistoSaida.getTotalSegundos()/3600);
        hPrevistoSaida.setMinutos((hPrevistoSaida.getTotalSegundos()%3600)/60);
        hPrevistoSaida.setSegundos(((hPrevistoSaida.getTotalSegundos()%3600)%60));
        String horaSaida = formatarHorario(hPrevistoSaida.getHoras(), hPrevistoSaida.getMinutos(), hPrevistoSaida.getSegundos());
        phSaida.setText(horaSaida);
    }

    //CALCULAR A HORA PARA VOLTAR DO ALMOÇO E A HORA DE SAIDA
    public void passo4(){
        phSairAlmoco.setText(hSairAlmoco.getText());
        Horario horarioEntrada;
        Horario hVoltaC;
        Horario hSaidaC;
        Horario hSaidaAl;
        Horario hVoltaAl = new Horario();
        Horario hPrevistoSaida;

        hSaidaC = configurarHorario(hSaidaCafe.getText().toString());
        hVoltaC = configurarHorario(hVoltaCafe.getText().toString());
        hSaidaAl = configurarHorario(hSairAlmoco.getText().toString());
        hVoltaAl.setTotalSegundos(hSaidaAl.getTotalSegundos()+5400);
        hVoltaAl = settingHorario(hVoltaAl.getTotalSegundos());
        String horaVoltaAlmoco = formatarHorario(
                hVoltaAl.getHoras(),
                hVoltaAl.getMinutos(),
                hVoltaAl.getSegundos()
        );
        phVoltaAlmoco.setText(horaVoltaAlmoco);

        horarioEntrada = configurarHorario(hEntrada.getText().toString());
        horarioEntrada.setTotalSegundos(horarioEntrada.getTotalSegundos()+(hVoltaC.getTotalSegundos()-hSaidaC.getTotalSegundos()));

        hPrevistoSaida = settingHorario(horarioEntrada.getTotalSegundos()+30600+5400);
        String horaSaida = formatarHorario(hPrevistoSaida.getHoras(), hPrevistoSaida.getMinutos(), hPrevistoSaida.getSegundos());
        phSaida.setText(horaSaida);
    }

    //CALCULAR A HORA DE SAIDA COM BASE NA HORA DA VOLTA DO ALMOÇO
    public void passo5(){
        Horario horarioEntrada;
        Horario hVoltaC;
        Horario hSaidaC;
        Horario hSaidaAl;
        Horario hVoltaAl;
        Horario hPrevistoSaida;

        hSaidaC = configurarHorario(hSaidaCafe.getText().toString());
        hVoltaC = configurarHorario(hVoltaCafe.getText().toString());
        hSaidaAl = configurarHorario(hSairAlmoco.getText().toString());
        hVoltaAl = configurarHorario(hVoltaAlmoco.getText().toString());
        horarioEntrada = configurarHorario(hEntrada.getText().toString());
        horarioEntrada.setTotalSegundos(horarioEntrada.getTotalSegundos()+(hVoltaC.getTotalSegundos()-hSaidaC.getTotalSegundos()) + (hVoltaAl.getTotalSegundos()-hSaidaAl.getTotalSegundos()));

        hPrevistoSaida = settingHorario(horarioEntrada.getTotalSegundos()+30600);
        String horaSaida = formatarHorario(hPrevistoSaida.getHoras(), hPrevistoSaida.getMinutos(), hPrevistoSaida.getSegundos());
        phSaida.setText(horaSaida);
    }

    //FUNÇÃO PARA RETORNAR UM OBJETO HORARIO JÁ FORMATADO PASSANFO UMA STRING COMO PARÂMETRO
    public Horario configurarHorario(String horario){
        Horario h = new Horario();
        String[] textoSeparado = horario.split(":");
        h.setHoras((Integer.parseInt(textoSeparado[0]))*3600);
        h.setMinutos((Integer.parseInt(textoSeparado[1]))*60);
        h.setSegundos(Integer.parseInt(textoSeparado[2]));
        h.setTotalSegundos(h.getHoras()+h.getMinutos()+h.getSegundos());
        return h;
    }

    //FUNÇÃO PARA RETORNAR UM OBJETO HORARIO JÁ FORMATADO PASSANDO UM INTERIO COMO PARAMENTRO
    public Horario settingHorario(int qtdSegundo){
        Horario horario = new Horario();
        horario.setTotalSegundos(qtdSegundo);
        horario.setHoras(horario.getTotalSegundos()/3600);
        horario.setMinutos((horario.getTotalSegundos()%3600)/60);
        horario.setSegundos(((horario.getTotalSegundos()%3600)%60));
        return horario;
    }

    //FAZ COM QUE OS NUMEROS MENORES QUE 10 NÃO SEJA DESCONSIDERADO O ZERO A ESQUERDA
    public String formatarHorario(int h, int m, int s){
        String hora;
        String minuto;
        String segundo;

        if(h < 10){
            hora = "0" + h;
        }else{
            hora = String.valueOf(h);
        }
        if (m < 10){
            minuto = "0" + m;
        }else {
            minuto = String.valueOf(m);
        }if (s < 10 ){
            segundo = "0" + s;
        }else {
            segundo = String.valueOf(s);
        }

        return hora+":"+minuto+":"+segundo;

    }

    public void calcularSaldoDeHoras(){
        Horario horarioEntrada;
        Horario hVoltaC;
        Horario hSaidaC;
        Horario hSaidaAl;
        Horario hVoltaAl;
        Horario horarioSaida;
        hSaidaC = configurarHorario(hSaidaCafe.getText().toString());
        hVoltaC = configurarHorario(hVoltaCafe.getText().toString());
        hSaidaAl = configurarHorario(hSairAlmoco.getText().toString());
        hVoltaAl = configurarHorario(hVoltaAlmoco.getText().toString());
        horarioEntrada = configurarHorario(hEntrada.getText().toString());
        horarioSaida = configurarHorario(hSaida.getText().toString());

        int saldoSegundos = 0 ;
        saldoSegundos = (hVoltaC.getTotalSegundos() - hSaidaC.getTotalSegundos()) + (hVoltaAl.getTotalSegundos() - hSaidaAl.getTotalSegundos());
        saldoSegundos = horarioSaida.getTotalSegundos() - horarioEntrada.getTotalSegundos() - saldoSegundos;
        saldoSegundos = saldoSegundos - 30600;

        if (saldoSegundos < 0){
            atrasoOuExtaras.setText("Atraso");
            saldoSegundos = saldoSegundos * -1;
        }else{
            atrasoOuExtaras.setText("Extra");
        }

        Horario horarioSaldo = settingHorario(saldoSegundos);
        String horaSaldo = formatarHorario(horarioSaldo.getHoras(), horarioSaldo.getMinutos(), horarioSaldo.getSegundos());
        saldo.setText(horaSaldo);

    }
}
