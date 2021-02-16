package me.t3sl4.market.util;

import java.util.List;

public class MessageUtil {
   public static String PREFIX;
   public static String MARKET_ISMI;
   public static String FIYAT;
   public static String ADET;
   public static String SLOT;
   public static String ESYA_SAHIBI;
   public static int SADECE_YETKILI_INFO_SATIRI_SAYISI;
   public static float VERGI_YUZDESI;
   public static boolean ORTAKLIK;
   public static List<String> INFO;
   public static String ZATEN_MARKET_VAR;
   public static String OYUNCU_BULUNAMADI;
   public static String ELINIZ_BOS;
   public static String SLOT_FAZLA;
   public static String YETERSIZ_BAKIYE;
   public static String MARKET_BULUNAMADI;
   public static String SLOT_ZATEN_DOLU;
   public static String HATA;
   public static String CONSOLE;
   public static String PERMERROR;
   public static String MARKET_BULUNAMADI_ORTAKLIK;
   public static String ORTAKLIK_KAPALI;
   public static String ONLINE;
   public static String KENDINI_ORTAK_EKLEYEMEZSIN;
   public static String ORTAK_BULUNAMADI;
   public static String ORTAKLIK_DEVREDISI;
   public static String ZATEN_ORTAK;
   public static String ORTAK_DEGILSIN;
   public static String ORTAKLIK_ZATEN_AKTIF;
   public static String ORTAKLIK_ZATEN_KAPALI;
   public static String ZATEN_ORTAKSIN;
   public static String ESYAYI_SEN_EKLEMEDIN;
   public static String MARKET_YARATILDI;
   public static String ITEM_KALDIRDILDI;
   public static String ITEM_EKLENDI;
   public static String KENDI_MARKETINDEN_ITEM_ALAMAZSIN;
   public static String ITEM_ALINDI;
   public static String SATIN_ALMA_BASARILI;
   public static String RELOAD;
   public static String ORTAKLIK_AKTIF;
   public static String ORTAKLIK_DEAKTIF;
   public static String ORTAK_EKLENDI;
   public static String ORTAK_OLARAK_EKLENDINIZ;
   public static String ORTAK_SILINDI;
   public static String ORTAKLIKTAN_CIKARILDINIZ;
   public static String MEVCUT_ORTAKLARIN;
   public static String ORTAK_SIRALAMASI;
   public static String AYRILDINIZ;
   public static String AYRILDI;
   public static String ORTAKLIKD_AKTIF;
   public static String ORTAKLIKD_PASIF;
   public static String MARKET_SAHIBI;
   public static String MARKET_SAHIP;
   public static String MARKETTEN_ITEM_KALDIRILDI;
   public static String MARKETTEN_ITEM_KALDIRILAMADI;
   public static String EKLE_KULLANIM;
   public static String KALDIRMA_KULLANIM;
   public static String KUR_KULLANIM;
   public static String ORTAKLIKD_KULLANIM;
   public static String ORTAKLIK_KULLANIM;
   public static String ORTAKEKLE_KULLANIM;
   public static String ORTAKSIL_KULLANIM;
   public static String ORTAKLAR_KULLANIM;
   public static String AYRIL_KULLANIM;
   public static String RELOAD_KULLANIM;

   static SettingsManager manager = SettingsManager.getInstance();

   public static void loadMessages() {
      PREFIX = colorize(manager.getConfig().getString("Prefix"));
      MARKET_ISMI = colorize(manager.getConfig().getString("Settings.MarketIsmi"));
      FIYAT = colorize(manager.getConfig().getString("Settings.Fiyat"));
      ADET = colorize(manager.getConfig().getString("Settings.Adet"));
      SLOT = colorize(manager.getConfig().getString("Settings.Slot"));
      ESYA_SAHIBI = colorize(manager.getConfig().getString("Settings.EsyaSahibi"));
      SADECE_YETKILI_INFO_SATIRI_SAYISI = manager.getConfig().getInt("Settings.SadeceYetkiliInfoSatiriSayisi");
      VERGI_YUZDESI = manager.getConfig().getInt("Settings.VergiYuzdesi");
      ORTAKLIK = manager.getConfig().getBoolean("Settings.Ortaklik");
      INFO = colorizeList(manager.getConfig().getStringList("Info"));
      ZATEN_MARKET_VAR = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.ZatenMarketVar"));
      OYUNCU_BULUNAMADI = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.OyuncuBulunamadi"));
      ELINIZ_BOS = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.ElinizBos"));
      SLOT_FAZLA = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.SlotFazla"));
      YETERSIZ_BAKIYE = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.YetersizBakiye"));
      MARKET_BULUNAMADI = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.MarketBulunamadi"));
      SLOT_ZATEN_DOLU = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.SlotZatenDolu"));
      HATA = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.Hata"));
      CONSOLE = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.Console"));
      PERMERROR = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.PermError"));
      MARKET_BULUNAMADI_ORTAKLIK = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.MarketBulunamadiOrtaklik"));
      ORTAKLIK_KAPALI = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.OrtaklikKapali"));
      ONLINE = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.Online"));
      KENDINI_ORTAK_EKLEYEMEZSIN = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.KendiniOrtakEkleyemezsin"));
      ORTAK_BULUNAMADI = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.OrtakBulunamadi"));
      ORTAKLIK_DEVREDISI = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.OrtaklikDevredisi"));
      ZATEN_ORTAK = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.ZatenOrtak"));
      ORTAK_DEGILSIN = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.OrtakDegilsin"));
      ORTAKLIK_ZATEN_AKTIF = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.OrtaklikZatenAktif"));
      ORTAKLIK_ZATEN_KAPALI = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.OrtaklikZatenKapali"));
      ZATEN_ORTAKSIN = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.ZatenOrtaksin"));
      ESYAYI_SEN_EKLEMEDIN = PREFIX + colorize(manager.getConfig().getString("Messages.ErrorMessages.EsyayiSenEklemedin"));
      MARKET_YARATILDI = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.MarketYaratildi"));
      ITEM_KALDIRDILDI = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.ItemKaldirildi"));
      ITEM_EKLENDI = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.ItemEklendi"));
      KENDI_MARKETINDEN_ITEM_ALAMAZSIN = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.KendiMarketindenItemAlamazsin"));
      ITEM_ALINDI = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.ItemAlindi"));
      SATIN_ALMA_BASARILI = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.SatinAlmaBasarili"));
      RELOAD = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.Reload"));
      ORTAKLIK_AKTIF = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.OrtaklikAktif"));
      ORTAKLIK_DEAKTIF = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.OrtaklikDeAktif"));
      ORTAK_EKLENDI = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.OrtakEklendi"));
      ORTAK_OLARAK_EKLENDINIZ = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.OrtakOlarakEklendiniz"));
      ORTAK_SILINDI = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.OrtakSilindi"));
      ORTAKLIKTAN_CIKARILDINIZ = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.OrtakliktanCikarildiniz"));
      MEVCUT_ORTAKLARIN = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.MevcutOrtaklarin"));
      ORTAK_SIRALAMASI = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.OrtakSiralamasi"));
      AYRILDINIZ = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.Ayrildiniz"));
      AYRILDI = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.Ayrildi"));
      ORTAKLIKD_AKTIF = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.OrtaklikdAktif"));
      ORTAKLIKD_PASIF = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.OrtaklikdPasif"));
      MARKET_SAHIBI = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.MarketSahibi"));
      MARKET_SAHIP = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.MarketSahip"));
      MARKETTEN_ITEM_KALDIRILDI = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.MarkettenItemKaldirildi"));
      MARKETTEN_ITEM_KALDIRILAMADI = PREFIX + colorize(manager.getConfig().getString("Messages.SuccessMessages.MarkettenItemKaldirilamadi"));
      EKLE_KULLANIM = PREFIX + colorize(manager.getConfig().getString("Messages.UsageMessages.EkleKullanim"));
      KALDIRMA_KULLANIM = PREFIX + colorize(manager.getConfig().getString("Messages.UsageMessages.KaldirmaKullanim"));
      KUR_KULLANIM = PREFIX + colorize(manager.getConfig().getString("Messages.UsageMessages.KurKullanim"));
      ORTAKLIKD_KULLANIM = PREFIX + colorize(manager.getConfig().getString("Messages.UsageMessages.OrtaklikdKullanim"));
      ORTAKLIK_KULLANIM = PREFIX + colorize(manager.getConfig().getString("Messages.UsageMessages.OrtaklikKullanim"));
      ORTAKEKLE_KULLANIM = PREFIX + colorize(manager.getConfig().getString("Messages.UsageMessages.OrtakekleKullanim"));
      ORTAKSIL_KULLANIM = PREFIX + colorize(manager.getConfig().getString("Messages.UsageMessages.OrtaksilKullanim"));
      ORTAKLAR_KULLANIM = PREFIX + colorize(manager.getConfig().getString("Messages.UsageMessages.OrtaklarKullanim"));
      AYRIL_KULLANIM = PREFIX + colorize(manager.getConfig().getString("Messages.UsageMessages.AyrilKullanim"));
      RELOAD_KULLANIM = PREFIX + colorize(manager.getConfig().getString("Messages.UsageMessages.ReloadKullanim"));
   }

   public static String colorize(String str) {
      return str.replace("&", "§");
   }

   public static List<String> colorizeList(List<String> str) {
      for(int x=0; x<str.size(); x++) {
         str.set(x, str.get(x).replace("&", "§"));
      }
      return str;
   }
}
