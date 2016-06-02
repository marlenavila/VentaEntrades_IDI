package com.example.marlen.ventaentrades_idi;

/**
 * Created by marlen on 01/06/2016.
 */
public class Butaques {
    private long num;

    Butaques(long n)
    {
        num = n;
    }


   /* private void _checkindex(int i)
    {
        if ( i < 1 || i >= 41 )
            throw new IndexOutOfBoundsException("No existe la butaca " + i);
    }*/

    public long consultarLong(){
        return num;
    }

    public boolean consultarbutaca(int i)
    {
       // _checkindex(i);
        return (num&(1L<<i)) != 0;
    }

    /*
       Marcar butaca i com ocupada

       @param i num butaca
    */
    public void marcarbutaca(int i)
    {
        //_checkindex(i);
        num |= (1L<<i);
    }

    /*
       Cambia el valor de una butaca
       Si esta en 0 lo pone a 1; si esta en 1 lo pone a 0.

       @param i la butaca
       @return El nuevo estado de la butaca
    */
    public long canviarbutaca(int i)
    {
       // _checkindex(i);
        long caca = (num^= (1L<<i)) ;
        return caca;
    }

    public long getButaques(){
        return num;
    }

    public  String mytoString()
    {
        return Long.toBinaryString(num);
    }

    public String mytoString2()
    {
        String r = "";
        long x = num>>>1;
        for ( int i = 1; i <= 40; ++i)
        {
            r += String.valueOf(x&1);
            x >>>= 1;
        }
        return r;
    }

}
