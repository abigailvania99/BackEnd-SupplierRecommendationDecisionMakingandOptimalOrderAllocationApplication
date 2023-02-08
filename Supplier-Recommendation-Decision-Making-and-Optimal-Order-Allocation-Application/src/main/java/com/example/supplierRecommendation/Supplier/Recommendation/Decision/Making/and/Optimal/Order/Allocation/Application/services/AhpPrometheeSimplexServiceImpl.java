package com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.services;

import com.example.supplierRecommendation.Supplier.Recommendation.Decision.Making.and.Optimal.Order.Allocation.Application.entities.*;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Arrays;

@Service
public class AhpPrometheeSimplexServiceImpl implements AhpPrometheeSimplexService {
    @Override
    public AhpResponse countAhp(AhpReq param) {

        double[][] perbandinganBerpasangan = new double[param.getLength()][param.getLength()];
        double[][] normalisasi = new double[param.getLength()][param.getLength()];
        double[] bobotPrioritas = new double[param.getLength()];
        double[][] perbandinganBerpasanganDikaliBobot = new double[param.getLength()][param.getLength()];
        double[][] pembagianDenganBobot = new double[param.getLength()][param.getLength()];
        double lambdaX;
        double CI;
        double CR;

        double[][] tempResult = new double[param.getLength()][param.getLength()];
        double[] total = new double[param.getLength()];
        int[] paramMatrix = param.getMatrix();
        double[] RI = new double[] {0.0,0.0,0.58,0.9,1.12,1.24,1.32,1.41,1.45,1.49};

        DecimalFormat df = new DecimalFormat("#.####");

        int k=0;
        for(int i=0;i<tempResult.length;i++){
            for(int j=0;j<tempResult[i].length;j++){
                if(i==j){
                    tempResult[i][j]=1;
                }
                else if(i<j){
                    tempResult[i][j]= paramMatrix[k];
                    k+=1;
                }
                else{
                    tempResult[i][j] = Double.parseDouble(df.format(1/tempResult[j][i]));
                }
                    total[j] += Double.parseDouble(df.format(tempResult[i][j]));
            }
        }
        perbandinganBerpasangan = tempResult;
        System.out.println("ini perbandingan  "+ Arrays.deepToString(perbandinganBerpasangan));
        normalisasi = normalisasiAHP(perbandinganBerpasangan,total, param.getLength());
        Arrays.fill(total, 0.0);
        System.out.println("ini total "+ Arrays.toString(total));


        for(int i=0;i<normalisasi.length;i++) {
            for (int j = 0; j < normalisasi[i].length; j++) {
                total[i] += Double.parseDouble(df.format(normalisasi[i][j]));
            }
        }

        for(int i=0;i< total.length; i++){
            total[i] = Double.parseDouble(df.format(total[i]));
        }

        System.out.println("ini total normalisasi "+ Arrays.toString(total));

        bobotPrioritas = bobotPrioritas(total);

        System.out.println("ini bobot "+ Arrays.toString(bobotPrioritas));

        perbandinganBerpasanganDikaliBobot = perbandinganBerpasanganDikaliBobot(perbandinganBerpasangan,bobotPrioritas);

        System.out.println("ini perbandingan berpasangan dikali bobot"+ Arrays.deepToString(perbandinganBerpasanganDikaliBobot));

        pembagianDenganBobot = pembagianDenganBobot(perbandinganBerpasanganDikaliBobot,bobotPrioritas);
        System.out.println("ini bagi bobot"+ Arrays.deepToString(pembagianDenganBobot));

        lambdaX = hitungLambdaX(pembagianDenganBobot);

        System.out.println("ini lambdaX "+ lambdaX);

        CI = Double.parseDouble(df.format((lambdaX-param.getLength())/(param.getLength()-1)));
        System.out.println("ini CI "+CI);

        CR = Double.parseDouble(df.format(CI/RI[param.getLength()-1]));
        System.out.println("ini CR "+CR);

        return new AhpResponse(perbandinganBerpasangan,normalisasi,bobotPrioritas,perbandinganBerpasanganDikaliBobot,pembagianDenganBobot,lambdaX,CI,CR);
    }

    @Override
    public PrometheeResponse countPromethee(PrometheeReq param) {
        DecimalFormat df = new DecimalFormat("#.###");

        int baris = param.getLengthSupplier();
        int kolom = param.getBobot().length;
        double[] mtrx = param.getMatrix();
        double[][] tempResult = new double[baris][kolom];

        double[][] nilaiAlternatif = new double[baris+2][kolom]; //termasuk minmax
        double[][] normalisasi = new double[param.getLengthSupplier()][param.getBobot().length];
        double[][] selisih = new double[param.getLengthSupplier()*(param.getLengthSupplier())-1][param.getBobot().length];
        double[][] nilaiPreferensi = new double[param.getLengthSupplier()*(param.getLengthSupplier())-1][param.getBobot().length];
        double[][] nilaiIndeksPreferensiMultiKriteria = new double[param.getLengthSupplier()*(param.getLengthSupplier())-1][param.getBobot().length];
        double[][] aggregatPreferensi = new double[param.getLengthSupplier()][param.getLengthSupplier()];
        double[] enteringFlow = new double[param.getLengthSupplier()];
        double[] leavingFlow = new double[param.getLengthSupplier()];
        double[] netFlow = new double[param.getLengthSupplier()];
        Double[] copyNetFlow = new Double[param.getLengthSupplier()];
        Double[] netFlowDouble = new Double[param.getLengthSupplier()];
        int[] rank = new int[param.getLengthSupplier()];

        double[] max = new double[param.getBobot().length];
        double[] min =new double[param.getBobot().length];
        String[] type = new String[param.getBobot().length];

        //ubah ke matriks
        int k=0;
        for(int i=0;i<baris;i++){
            for(int j=0; j<kolom;j++){
                tempResult[i][j] = mtrx[k];
                k++;
            }
        }

        //cari min max

        for(int i=0;i < kolom ;i++){
            double maxm = tempResult[0][i];
            double minm = tempResult[0][i];
            for(int j=0; j < baris;j++){
                if(maxm<tempResult[j][i]){
                    maxm = tempResult[j][i];
                }
                if(minm>tempResult[j][i]){
                    minm = tempResult[j][i];
                }
            }
            max[i]=maxm;
            min[i]=minm;
        }


        for(int i=0; i<baris+2; i++){
            for(int j=0; j<kolom; j++){
                if(i == baris+1 || i == baris){
                    if(i==baris){
                        nilaiAlternatif[i][j] = max[j];
                    }
                    else{
                        nilaiAlternatif[i][j] = min[j];

                    }
                }else
                nilaiAlternatif[i][j] = tempResult[i][j];
            }
        }

        System.out.println(Arrays.deepToString(nilaiAlternatif));

        normalisasi = normalisasiPromethee(min, max, tempResult, param.getType(), baris, kolom);

        System.out.println(Arrays.deepToString(normalisasi));

        selisih = distance(normalisasi, baris, kolom);

        System.out.println(Arrays.deepToString(selisih));

        nilaiPreferensi = preferensi(selisih);

        System.out.println(Arrays.deepToString(nilaiPreferensi));

        nilaiIndeksPreferensiMultiKriteria = nilaiIndeksPreferensiMultikriteria(nilaiPreferensi, param.getBobot(), baris, kolom);

        System.out.println(Arrays.deepToString(nilaiIndeksPreferensiMultiKriteria));

        aggregatPreferensi = aggregatPreferensi(nilaiIndeksPreferensiMultiKriteria,baris,kolom);

        System.out.println(Arrays.deepToString(aggregatPreferensi));

        leavingFlow = leavingFlow(aggregatPreferensi, baris);

        System.out.println(Arrays.toString(leavingFlow));
        
        enteringFlow = enteringFlow(aggregatPreferensi, baris);

        System.out.println(Arrays.toString(enteringFlow));

        netFlow = netFlow(enteringFlow, leavingFlow, baris);

        System.out.println(Arrays.toString(netFlow));


        for(int i=0; i< netFlow.length;i++){
            copyNetFlow[i] = new Double(netFlow[i]);
            netFlowDouble[i] = new Double(netFlow[i]);
        }

        Arrays.sort(copyNetFlow);

        for(int i=0;i<baris; i++){
            rank[baris-1-i] = Arrays.asList(netFlowDouble).indexOf(copyNetFlow[i]);
        }

        System.out.println(Arrays.toString(rank));

        return new PrometheeResponse(nilaiAlternatif,normalisasi,selisih,nilaiPreferensi,nilaiIndeksPreferensiMultiKriteria,aggregatPreferensi,enteringFlow,leavingFlow,netFlow,rank);
    }

    @Override
    public SimplexResponse countSimplex(SimplexReq param) {
        int M=1000000;
        int posCol, posRow =1;
        double elmPivot=0;
        double[][] hasil = new double[9][2];
        double[][] inputan = {{param.getInputMatrix()[8],param.getInputMatrix()[9],param.getInputMatrix()[10],param.getInputMatrix()[11],0},
                {1,1,0,0,param.getInputMatrix()[1]},
                {0,0,1,1,param.getInputMatrix()[3]},
                {1,1,0,0,param.getInputMatrix()[0]},
                {1,0,0,0,param.getInputMatrix()[4]},
                {0,1,0,0,param.getInputMatrix()[5]},
                {0,0,1,1,param.getInputMatrix()[2]},
                {0,0,1,0,param.getInputMatrix()[6]},
                {0,0,0,1,param.getInputMatrix()[7]}};

        double[][] matriksAwal = new double[10][20];

        inputan[0][4] = (inputan[3][4]+inputan[4][4]+inputan[5][4]+inputan[6][4]+inputan[7][4]+
                inputan[8][4])*M;
        inputan[0][0] = 2*M-inputan[0][0];
        inputan[0][1] = 2*M-inputan[0][1];
        inputan[0][2] = 2*M-inputan[0][2];
        inputan[0][3] = 2*M-inputan[0][3];

        for(int i=0;i<matriksAwal[0].length;i++){
            if(i<11) //variabel ada 4 dan >= ada 6 jadi cmn 5
                matriksAwal[0][i]=i;
        }
        for(int i=2; i<matriksAwal.length;i++){ //mulai dari 2 krn z tidak dihitung
            matriksAwal[i][0] = 9+i;// jadi mulai dari 11
        }

        for(int i=0 ;i<inputan.length; i++){
            for(int j=0; j<inputan[i].length;j++){
                if(j!=4){ //validasi dari inputan
                    matriksAwal[i+1][j+1] = inputan[i][j];
                }
                else{
                    matriksAwal[i+1][19] = inputan[i][j];
                }
            }
        }


        matriksAwal[1][5] = matriksAwal[1][6] = matriksAwal[1][7] = matriksAwal[1][8]
                = matriksAwal[1][9] = matriksAwal[1][10] = -1*M;

        for(int i=2; i< matriksAwal.length;i++){
            matriksAwal[i][i+9]= 1; //mulai dri kolom ke11
            if(i>=4){
                matriksAwal[i][i+1] = -1;
            }

        }

        int loop =1;
        //cek, gk boleh ada yg lebih dari 0 atau positif
        int z=0;
        for(int i=1; i<matriksAwal[1].length-1;i++){
            if (matriksAwal[1][i] > 0) {
                z = 1;
                break;
            }
        }
        while(z==1){
            posCol = pivotCol(matriksAwal); //posisi kolom
            posRow = pivotRow(matriksAwal, posCol); //posisi baris
            matriksAwal[posRow][0] = posCol; //ini buat update indeksnya
            elmPivot = matriksAwal[posRow][posCol];

            for(int i=1; i<matriksAwal[1].length;i++){
                matriksAwal[posRow][i] = matriksAwal[posRow][i]/elmPivot;
            }
            for(int i=1; i<matriksAwal.length; i++){
                double temp = matriksAwal[i][posCol];
                for(int j=1; j<matriksAwal[1].length;j++){
                    if(i != posRow){
                        if(i==1 && j==19){
                            System.out.println("cek");
                            System.out.println(matriksAwal[i][j]);
                            System.out.println((temp*matriksAwal[posRow][j]));
                        }

                        matriksAwal[i][j] = matriksAwal[i][j] - (temp*matriksAwal[posRow][j]);

                    }
                }
            }

            z=0;
            System.out.println("iterasi ke-" + loop+" "+Arrays.deepToString(matriksAwal));
            loop++;


            for(int i=1; i<matriksAwal[1].length-1;i++){
                if(matriksAwal[1][i] >0){
                    z=1;
                    break;
                }
            }
        }

        for(int i=1; i< matriksAwal.length; i++){
            hasil[i-1][0]= matriksAwal[i][0];
            hasil[i-1][1] = matriksAwal[i][19];
        }
        SimplexResponse simplexResponse = new SimplexResponse(hasil);
        return simplexResponse;
    }
    public int pivotCol(double[][] matriks){
        double max = matriks[1][1];
        int pos =1;

        for(int i=1; i<matriks[1].length-1; i++){ //sampai length-1 krn ruas kanan gk ikut
            if(max < matriks[1][i]){
                max = matriks[1][i];
                pos=i;
            }
        }

        return pos;
    }

    public int pivotRow(double[][] matriks, int pos){
        int piv =1;
        double minm = matriks[2][19]/matriks[2][pos]; //z tidak dihitung ratio
        for(int i=2; i<=matriks.length-1;i++){
            if(matriks[i][19]/matriks[i][pos]>0){
                if(minm > matriks[i][19]/matriks[i][pos]){
                    minm = matriks[i][19]/matriks[i][pos];
                    piv = i;
                }
            }
        }
        return piv;
    }

    public double[] netFlow(double[] enteringFlow, double[] leavingFlow, int baris) {
        DecimalFormat df = new DecimalFormat("#.###");
        double[] netFlow = new double[baris];

        for(int i=0; i<enteringFlow.length;i++){
            netFlow[i] = leavingFlow[i]-enteringFlow[i];
            netFlow[i] = Double.parseDouble(df.format(netFlow[i]));
        }

        return netFlow;

    }

    //entering flow
    public double[] enteringFlow(double[][] aggregatPreferensi, int baris) {
        DecimalFormat df = new DecimalFormat("#.###");
        double[] enteringFlow = new double[baris];

        for(int i=0;i<aggregatPreferensi[0].length;i++){
            for(int j=0; j<aggregatPreferensi.length; j++){
                enteringFlow[i] += aggregatPreferensi[j][i]/(aggregatPreferensi.length-1);
            }
            enteringFlow[i] = Double.parseDouble(df.format(enteringFlow[i]));
        }

        return enteringFlow;
    }


    //leaving flow
    public double[] leavingFlow(double[][] aggregatPreferensi, int baris) {
        DecimalFormat df = new DecimalFormat("#.###");
        double[] leavingFlow = new double[baris];

        for(int i=0; i<aggregatPreferensi.length;i++){
            for(int j=0; j<aggregatPreferensi[i].length;j++){
                leavingFlow[i] += aggregatPreferensi[i][j]/(aggregatPreferensi.length-1);
            }
            leavingFlow[i] = Double.parseDouble(df.format(leavingFlow[i]));
        }
        
        return leavingFlow;
    }

    public double[][] nilaiIndeksPreferensiMultikriteria(double[][] nilaiPreferensi, double[] bobot, int baris, int kolom){
        DecimalFormat df = new DecimalFormat("#.###");

        double[][] hasilPerkalian = new double[baris*(baris-1)][kolom];
        for(int i=0; i< nilaiPreferensi.length; i++){
            for(int j=0; j<kolom; j++){
                hasilPerkalian[i][j] = Double.parseDouble(df.format(bobot[j]*nilaiPreferensi[i][j]));

            }
        }

        return hasilPerkalian;
    }

    public double[][] aggregatPreferensi(double[][] hasilPerkalian, int baris, int kolom) {
        DecimalFormat df = new DecimalFormat("#.###");

        double[] jumlahBaris = new double[baris*(baris-1)];
        for(int i=0; i< baris*(baris-1); i++){
            for(int j=0; j<kolom; j++){
                jumlahBaris[i] += Double.parseDouble(df.format(hasilPerkalian[i][j]));
            }
        }

        double[][] aggregatedPref = new double[baris][baris];

        int temp=0;
        for(int i=0;i<baris;i++){
            for(int j=0; j<baris; j++){
                if(i == j){
                    aggregatedPref[i][j] =0;
                }
                else{
                    aggregatedPref[i][j] = Double.parseDouble(df.format(jumlahBaris[temp]));
                    temp++;
                }
            }
        }
        return aggregatedPref;
    }

    public double[][] preferensi(double[][] distance) {

        double[][] nilaiPreferensi = new double[distance.length][distance[0].length];
        for(int i=0 ; i< distance.length;i++){
            for(int j=0; j< distance[i].length;j++){
                if(distance[i][j]<0){
                    nilaiPreferensi[i][j]=0;
                }
                else{
                    nilaiPreferensi[i][j]=distance[i][j];
                }

            }
        }

        return nilaiPreferensi;
    }

    public double[][] distance(double[][] normalisasi, int baris, int kolom) {
        DecimalFormat df = new DecimalFormat("#.###");
        double[][] selisih = new double[baris*(baris-1)][kolom];

        int m =0;
        for(int i=0; i< baris;i++){
            for(int j=0;j < baris;j++){
                for(int k=0; k<kolom; k++){
                    if(i==j){
                        break;
                    }
                    selisih[m][k] = Double.parseDouble(df.format(normalisasi[i][k]-normalisasi[j][k]));
                }
                if(i != j){
                    m++;
                }
            }
        }

        return selisih;
    }

    public double[][] normalisasiPromethee(double[] min, double[] max, double[][] mtrx, String[] type, int baris, int kolom) {

        DecimalFormat df = new DecimalFormat("#.###");
        double[][] mtrxNorm= new double[baris][kolom];

        for(int i=0;i< baris;i++){
            for(int j=0; j<kolom;j++){
                if(type[j].equalsIgnoreCase("Benefit")){
                    if((mtrx[i][j]-min[j]) == 0.0){
                        mtrxNorm[i][j]=0;
                    }else {
                        mtrxNorm[i][j] = Double.parseDouble(df.format((mtrx[i][j] - min[j]) / (max[j] - min[j])));
                    }
                }
                else if(type[j].equalsIgnoreCase("Cost")){
                    if((max[j]-mtrx[i][j]) == 0.0){
                        mtrxNorm[i][j]=0;
                    }else {
                        mtrxNorm[i][j] = Double.parseDouble(df.format((max[j]-mtrx[i][j]) / (max[j] - min[j])));
                    }
                }
            }
        }

        return mtrxNorm;
    }

    public double[][] normalisasiAHP(double[][] matriks, double[] total, int length) {

        DecimalFormat df = new DecimalFormat("#.####");

        double[][] normalisasi = new double[length][length];

        for (int i = 0; i < matriks.length; i++) {
            for (int j = 0; j < matriks[i].length; j++) {
                normalisasi[i][j] = Double.parseDouble(df.format(matriks[i][j] / total[j]));
            }
        }

        System.out.println("ini normalisasi "+ Arrays.deepToString(normalisasi));
        return normalisasi;

    }

    public double[] bobotPrioritas(double[] total){

        double[] bobot = new double[total.length];
        DecimalFormat df = new DecimalFormat("#.####");

        for(int i=0; i< total.length; i++){
            bobot[i] = Double.parseDouble(df.format(total[i]/ total.length));
        }

        return bobot;
    }

    public double[][] perbandinganBerpasanganDikaliBobot(double[][] perbandinganBerpasangan, double[] bobotPrioritas) {
        double[][] bobotTranspose = new double[bobotPrioritas.length][1];
        double jumlah=0;
        double[][] perbandinganDikaliBobot = new double[bobotPrioritas.length][1];
        DecimalFormat df = new DecimalFormat("#.####");

        for(int i=0;i< bobotPrioritas.length;i++){
            for(int j=0;j<1;j++){
                bobotTranspose[i][j]=bobotPrioritas[i];
            }
        }

        for (int i = 0; i < perbandinganBerpasangan.length; i++) {
            for (int j = 0; j < 1; j++) {
                for (int k = 0; k < bobotTranspose.length; k++) {
                    jumlah = jumlah + perbandinganBerpasangan[i][k] * bobotTranspose[k][j];
                }
                perbandinganDikaliBobot[i][j] = Double.parseDouble(df.format(jumlah));
                jumlah = 0;
            }
        }

        return perbandinganDikaliBobot;

    }

    public double[][] pembagianDenganBobot(double[][] perbandinganBerpasanganDikaliBobot, double[] bobotPrioritas) {
        double[][] bobotTranspose = new double[bobotPrioritas.length][1];
        double[][] pembagianDenganBobotResult= new double[bobotPrioritas.length][1];
        DecimalFormat df = new DecimalFormat("#.####");

        for(int i=0;i< bobotPrioritas.length;i++){
            for(int j=0;j<1;j++){
                bobotTranspose[i][j]=bobotPrioritas[i];
            }
        }
        for (int i=0;i< perbandinganBerpasanganDikaliBobot.length;i++){
            for(int j=0 ; j< perbandinganBerpasanganDikaliBobot[i].length;j++){
                pembagianDenganBobotResult[i][j] = Double.parseDouble(df.format(perbandinganBerpasanganDikaliBobot[i][j]/bobotTranspose[i][j]));
//                totalHasilBagi += Float.parseFloat(df.format(konsistensi[i][j]/bobot[i][j]));
            }
        }

        return pembagianDenganBobotResult;
    }

    public double hitungLambdaX(double[][] pembagianDenganBobot) {
        DecimalFormat df = new DecimalFormat("#.####");
        double totalHasilBagi = 0;
        double lambda =0;
        double ordeMatriks= pembagianDenganBobot.length;

        for (int i=0;i< pembagianDenganBobot.length;i++){
            for(int j=0 ; j< pembagianDenganBobot[i].length;j++){
                totalHasilBagi += Double.parseDouble(df.format(pembagianDenganBobot[i][j]));
            }
        }

        lambda = Double.parseDouble(df.format(totalHasilBagi/ordeMatriks));

        return lambda;
    }

}
