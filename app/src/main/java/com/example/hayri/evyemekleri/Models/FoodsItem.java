package com.example.hayri.evyemekleri.Models;

public class FoodsItem{
	private String kulid;
	private String yemekfiyat;
	private String yorumpuani;
	private String yemekresim;
	private String yemekid;
	private String sehirid;
	private String miktar;
	private String yemekadi;
	private String katid;

	public void setKulId(String kulid){
		this.kulid = kulid;
	}

	public String getKulid(){
		return kulid;
	}

	public void setYemekFiyat(String yemekfiyat){
		this.yemekfiyat = yemekfiyat;
	}

	public String getYemekFiyat(){
		return yemekfiyat;
	}

	public void setYorumPuani(String yorumpuani){
		this.yorumpuani = yorumpuani;
	}

	public String getYorumPuani(){
		return yorumpuani;
	}

	public void setYemekresim(String yemekresim){
		this.yemekresim = yemekresim;
	}

	public String getYemekresim(){
		return yemekresim;
	}

	public void setYemekId(String yemekid){
		this.yemekid = yemekid;
	}

	public String getYemekId(){
		return yemekid;
	}

	public void setSehirId(String sehirid){
		this.sehirid = sehirid;
	}

	public String getSehirId(){
		return sehirid;
	}

	public void setMiktar(String miktar){
		this.miktar = miktar;
	}

	public String getMiktar(){
		return miktar;
	}

	public void setYemekAdi(String yemekadi){
		this.yemekadi = yemekadi;
	}

	public String getYemekAdi(){
		return yemekadi;
	}

	public void setKatId(String katid){
		this.katid = katid;
	}

	public String getKatId(){
		return katid;
	}

	@Override
	public String toString(){
		return
				"FoodsItem{" +
						"kul_id = '" + kulid + '\'' +
						",yemek_fiyat = '" + yemekfiyat + '\'' +
						",yorum_puani = '" + yorumpuani + '\'' +
						",yemekresim = '" + yemekresim + '\'' +
						",yemek_id = '" + yemekid + '\'' +
						",sehir_id = '" + sehirid + '\'' +
						",miktar = '" + miktar + '\'' +
						",yemek_adi = '" + yemekadi + '\'' +
						",kat_id = '" + katid + '\'' +
						"}";
	}
}