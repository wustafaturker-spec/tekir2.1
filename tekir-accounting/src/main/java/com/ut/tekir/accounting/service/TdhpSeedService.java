package com.ut.tekir.accounting.service;

import com.ut.tekir.accounting.entity.AccountPlan;
import com.ut.tekir.accounting.entity.ChartAccount;
import com.ut.tekir.accounting.enums.AccountType;
import com.ut.tekir.accounting.enums.NormalBalance;
import com.ut.tekir.accounting.repository.ChartAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ut.tekir.accounting.enums.AccountType.*;
import static com.ut.tekir.accounting.enums.NormalBalance.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TdhpSeedService {

    private final ChartAccountRepository chartAccountRepository;

    record AccountSeed(String code, String name, AccountType type, NormalBalance balance, boolean isDetail) {}

    private static final List<AccountSeed> TDHP_ACCOUNTS = List.of(
        // ============================================================
        // SINIF 1: DÖNEN VARLIKLAR
        // ============================================================
        new AccountSeed("1",    "DÖNEN VARLIKLAR",                                    ASSET, DEBIT, false),
        new AccountSeed("10",   "Hazır Değerler",                                      ASSET, DEBIT, false),
        new AccountSeed("100",  "Kasa Hesabı",                                         ASSET, DEBIT, false),
        new AccountSeed("1000", "TL Kasası",                                           ASSET, DEBIT, true),
        new AccountSeed("1001", "Yabancı Para Kasası",                                 ASSET, DEBIT, true),
        new AccountSeed("101",  "Alınan Çekler",                                       ASSET, DEBIT, false),
        new AccountSeed("1010", "Portföydeki Çekler",                                  ASSET, DEBIT, true),
        new AccountSeed("1011", "Tahsildeki Çekler",                                   ASSET, DEBIT, true),
        new AccountSeed("102",  "Bankalar",                                            ASSET, DEBIT, false),
        new AccountSeed("1020", "T.C. Merkez Bankası",                                 ASSET, DEBIT, true),
        new AccountSeed("1021", "Yurt İçi Bankalar",                                   ASSET, DEBIT, true),
        new AccountSeed("1022", "Yurt Dışı Bankalar",                                  ASSET, DEBIT, true),
        new AccountSeed("103",  "Verilen Çekler ve Ödeme Emirleri (-)",                ASSET, CREDIT, false),
        new AccountSeed("1030", "Verilen Çekler",                                      ASSET, CREDIT, true),
        new AccountSeed("108",  "Diğer Hazır Değerler",                                ASSET, DEBIT, true),
        new AccountSeed("11",   "Menkul Kıymetler",                                    ASSET, DEBIT, false),
        new AccountSeed("110",  "Hisse Senetleri",                                     ASSET, DEBIT, true),
        new AccountSeed("111",  "Özel Kesim Tahvil Senet ve Bonoları",                 ASSET, DEBIT, true),
        new AccountSeed("112",  "Kamu Kesimi Tahvil Senet ve Bonoları",                ASSET, DEBIT, true),
        new AccountSeed("118",  "Diğer Menkul Kıymetler",                              ASSET, DEBIT, true),
        new AccountSeed("119",  "Menkul Kıymetler Değer Düşüklüğü Karşılığı (-)",     ASSET, CREDIT, true),
        new AccountSeed("12",   "Ticari Alacaklar",                                    ASSET, DEBIT, false),
        new AccountSeed("120",  "Alıcılar",                                            ASSET, DEBIT, true),
        new AccountSeed("121",  "Alacak Senetleri",                                    ASSET, DEBIT, true),
        new AccountSeed("122",  "Alacak Senetleri Reeskontu (-)",                      ASSET, CREDIT, true),
        new AccountSeed("126",  "Verilen Depozito ve Teminatlar",                      ASSET, DEBIT, true),
        new AccountSeed("127",  "Diğer Ticari Alacaklar",                              ASSET, DEBIT, true),
        new AccountSeed("128",  "Şüpheli Ticari Alacaklar",                            ASSET, DEBIT, true),
        new AccountSeed("129",  "Şüpheli Ticari Alacaklar Karşılığı (-)",              ASSET, CREDIT, true),
        new AccountSeed("13",   "Diğer Alacaklar",                                     ASSET, DEBIT, false),
        new AccountSeed("131",  "Ortaklardan Alacaklar",                               ASSET, DEBIT, true),
        new AccountSeed("132",  "İştiraklerden Alacaklar",                             ASSET, DEBIT, true),
        new AccountSeed("133",  "Bağlı Ortaklıklardan Alacaklar",                      ASSET, DEBIT, true),
        new AccountSeed("135",  "Personelden Alacaklar",                               ASSET, DEBIT, true),
        new AccountSeed("136",  "Diğer Çeşitli Alacaklar",                             ASSET, DEBIT, true),
        new AccountSeed("137",  "Diğer Alacak Senetleri Reeskontu (-)",                ASSET, CREDIT, true),
        new AccountSeed("138",  "Şüpheli Diğer Alacaklar",                             ASSET, DEBIT, true),
        new AccountSeed("139",  "Şüpheli Diğer Alacaklar Karşılığı (-)",               ASSET, CREDIT, true),
        new AccountSeed("15",   "Stoklar",                                             ASSET, DEBIT, false),
        new AccountSeed("150",  "İlk Madde ve Malzeme",                                ASSET, DEBIT, true),
        new AccountSeed("151",  "Yarı Mamul - Üretim",                                 ASSET, DEBIT, true),
        new AccountSeed("152",  "Mamuller",                                            ASSET, DEBIT, true),
        new AccountSeed("153",  "Ticari Mallar",                                       ASSET, DEBIT, true),
        new AccountSeed("157",  "Diğer Stoklar",                                       ASSET, DEBIT, true),
        new AccountSeed("158",  "Stok Değer Düşüklüğü Karşılığı (-)",                 ASSET, CREDIT, true),
        new AccountSeed("159",  "Verilen Sipariş Avansları",                           ASSET, DEBIT, true),
        new AccountSeed("18",   "Gelecek Aylara Ait Giderler ve Gelir Tahakkukları",   ASSET, DEBIT, false),
        new AccountSeed("180",  "Gelecek Aylara Ait Giderler",                         ASSET, DEBIT, true),
        new AccountSeed("181",  "Gelir Tahakkukları",                                  ASSET, DEBIT, true),
        new AccountSeed("19",   "Diğer Dönen Varlıklar",                               ASSET, DEBIT, false),
        new AccountSeed("190",  "Devreden KDV",                                        ASSET, DEBIT, true),
        new AccountSeed("191",  "İndirilecek KDV",                                     ASSET, DEBIT, true),
        new AccountSeed("192",  "Diğer KDV",                                           ASSET, DEBIT, true),
        new AccountSeed("193",  "Peşin Ödenen Vergiler ve Fonlar",                     ASSET, DEBIT, true),
        new AccountSeed("195",  "İş Avansları",                                        ASSET, DEBIT, true),
        new AccountSeed("196",  "Personel Avansları",                                  ASSET, DEBIT, true),
        new AccountSeed("197",  "Sayım ve Tesellüm Noksanları",                        ASSET, DEBIT, true),
        new AccountSeed("198",  "Diğer Çeşitli Dönen Varlıklar",                       ASSET, DEBIT, true),
        new AccountSeed("199",  "Diğer Dönen Varlıklar Karşılığı (-)",                 ASSET, CREDIT, true),
        // ============================================================
        // SINIF 2: DURAN VARLIKLAR
        // ============================================================
        new AccountSeed("2",    "DURAN VARLIKLAR",                                     ASSET, DEBIT, false),
        new AccountSeed("22",   "Ticari Alacaklar",                                    ASSET, DEBIT, false),
        new AccountSeed("220",  "Alıcılar",                                            ASSET, DEBIT, true),
        new AccountSeed("221",  "Alacak Senetleri",                                    ASSET, DEBIT, true),
        new AccountSeed("226",  "Verilen Depozito ve Teminatlar",                      ASSET, DEBIT, true),
        new AccountSeed("25",   "Maddi Duran Varlıklar",                               ASSET, DEBIT, false),
        new AccountSeed("250",  "Arazi ve Arsalar",                                    ASSET, DEBIT, true),
        new AccountSeed("251",  "Yer Altı ve Yer Üstü Düzenleri",                      ASSET, DEBIT, true),
        new AccountSeed("252",  "Binalar",                                             ASSET, DEBIT, true),
        new AccountSeed("253",  "Tesis, Makine ve Cihazlar",                           ASSET, DEBIT, true),
        new AccountSeed("254",  "Taşıtlar",                                            ASSET, DEBIT, true),
        new AccountSeed("255",  "Demirbaşlar",                                         ASSET, DEBIT, true),
        new AccountSeed("256",  "Diğer Maddi Duran Varlıklar",                         ASSET, DEBIT, true),
        new AccountSeed("257",  "Birikmiş Amortismanlar (-)",                          ASSET, CREDIT, true),
        new AccountSeed("258",  "Yapılmakta Olan Yatırımlar",                          ASSET, DEBIT, true),
        new AccountSeed("259",  "Verilen Avanslar",                                    ASSET, DEBIT, true),
        new AccountSeed("26",   "Maddi Olmayan Duran Varlıklar",                       ASSET, DEBIT, false),
        new AccountSeed("260",  "Haklar",                                              ASSET, DEBIT, true),
        new AccountSeed("261",  "Şerefiye",                                            ASSET, DEBIT, true),
        new AccountSeed("262",  "Kuruluş ve Örgütlenme Giderleri",                     ASSET, DEBIT, true),
        new AccountSeed("263",  "Araştırma ve Geliştirme Giderleri",                   ASSET, DEBIT, true),
        new AccountSeed("264",  "Özel Maliyetler",                                     ASSET, DEBIT, true),
        new AccountSeed("267",  "Diğer Maddi Olmayan Duran Varlıklar",                 ASSET, DEBIT, true),
        new AccountSeed("268",  "Birikmiş İtfa Payları (-)",                           ASSET, CREDIT, true),
        new AccountSeed("28",   "Gelecek Yıllara Ait Giderler ve Gelir Tahakkukları",  ASSET, DEBIT, false),
        new AccountSeed("280",  "Gelecek Yıllara Ait Giderler",                        ASSET, DEBIT, true),
        new AccountSeed("281",  "Gelir Tahakkukları",                                  ASSET, DEBIT, true),
        // ============================================================
        // SINIF 3: KISA VADELİ YABANCI KAYNAKLAR
        // ============================================================
        new AccountSeed("3",    "KISA VADELİ YABANCI KAYNAKLAR",                      LIABILITY, CREDIT, false),
        new AccountSeed("30",   "Mali Borçlar",                                        LIABILITY, CREDIT, false),
        new AccountSeed("300",  "Banka Kredileri",                                     LIABILITY, CREDIT, true),
        new AccountSeed("301",  "Finansman Bonoları",                                  LIABILITY, CREDIT, true),
        new AccountSeed("302",  "Uzun Vadeli Kredilerin Anapara Taksitleri ve Faizleri", LIABILITY, CREDIT, true),
        new AccountSeed("303",  "Uzun Vadeli Kredilerin Anapara Taksitleri ve Faizleri (Dövizli)", LIABILITY, CREDIT, true),
        new AccountSeed("304",  "Tahvil Anapara Borç Taksit ve Faizleri",              LIABILITY, CREDIT, true),
        new AccountSeed("308",  "Menkul Kıymetler İhraç Farkı (-)",                   LIABILITY, DEBIT, true),
        new AccountSeed("309",  "Diğer Mali Borçlar",                                  LIABILITY, CREDIT, true),
        new AccountSeed("32",   "Ticari Borçlar",                                      LIABILITY, CREDIT, false),
        new AccountSeed("320",  "Satıcılar",                                           LIABILITY, CREDIT, true),
        new AccountSeed("321",  "Borç Senetleri",                                      LIABILITY, CREDIT, true),
        new AccountSeed("322",  "Borç Senetleri Reeskontu (-)",                        LIABILITY, DEBIT, true),
        new AccountSeed("326",  "Alınan Depozito ve Teminatlar",                       LIABILITY, CREDIT, true),
        new AccountSeed("329",  "Diğer Ticari Borçlar",                                LIABILITY, CREDIT, true),
        new AccountSeed("33",   "Diğer Borçlar",                                       LIABILITY, CREDIT, false),
        new AccountSeed("331",  "Ortaklara Borçlar",                                   LIABILITY, CREDIT, true),
        new AccountSeed("332",  "İştiraklere Borçlar",                                 LIABILITY, CREDIT, true),
        new AccountSeed("333",  "Bağlı Ortaklıklara Borçlar",                         LIABILITY, CREDIT, true),
        new AccountSeed("335",  "Personele Borçlar",                                   LIABILITY, CREDIT, true),
        new AccountSeed("336",  "Diğer Çeşitli Borçlar",                               LIABILITY, CREDIT, true),
        new AccountSeed("34",   "Alınan Avanslar",                                      LIABILITY, CREDIT, false),
        new AccountSeed("340",  "Alınan Sipariş Avansları",                            LIABILITY, CREDIT, true),
        new AccountSeed("349",  "Alınan Diğer Avanslar",                               LIABILITY, CREDIT, true),
        new AccountSeed("36",   "Ödenecek Vergi ve Yükümlülükler",                     LIABILITY, CREDIT, false),
        new AccountSeed("360",  "Ödenecek Vergi ve Fonlar",                            LIABILITY, CREDIT, true),
        new AccountSeed("361",  "Ödenecek Sosyal Güvenlik Kesintileri",                LIABILITY, CREDIT, true),
        new AccountSeed("368",  "Vadesi Geçmiş Ertelenmiş veya Taksitlendirilmiş Vergi ve Diğer Yükümlülükler", LIABILITY, CREDIT, true),
        new AccountSeed("369",  "Ödenecek Diğer Yükümlülükler",                        LIABILITY, CREDIT, true),
        new AccountSeed("37",   "Borç ve Gider Karşılıkları",                          LIABILITY, CREDIT, false),
        new AccountSeed("370",  "Dönem Karı Vergi ve Diğer Yasal Yükümlülük Karşılıkları", LIABILITY, CREDIT, true),
        new AccountSeed("371",  "Dönem Karının Peşin Ödenen Vergi ve Diğer Yükümlülükleri (-)", LIABILITY, DEBIT, true),
        new AccountSeed("372",  "Kıdem Tazminatı Karşılığı",                           LIABILITY, CREDIT, true),
        new AccountSeed("379",  "Diğer Borç ve Gider Karşılıkları",                    LIABILITY, CREDIT, true),
        new AccountSeed("38",   "Gelecek Aylara Ait Gelirler ve Gider Tahakkukları",   LIABILITY, CREDIT, false),
        new AccountSeed("380",  "Gelecek Aylara Ait Gelirler",                         LIABILITY, CREDIT, true),
        new AccountSeed("381",  "Gider Tahakkukları",                                  LIABILITY, CREDIT, true),
        new AccountSeed("39",   "Diğer Kısa Vadeli Yabancı Kaynaklar",                  LIABILITY, CREDIT, false),
        new AccountSeed("391",  "Hesaplanan KDV",                                      LIABILITY, CREDIT, true),
        new AccountSeed("392",  "Diğer KDV",                                           LIABILITY, CREDIT, true),
        new AccountSeed("397",  "Sayım ve Tesellüm Fazlaları",                         LIABILITY, CREDIT, true),
        new AccountSeed("399",  "Diğer Çeşitli Kısa Vadeli Yabancı Kaynaklar",         LIABILITY, CREDIT, true),
        // ============================================================
        // SINIF 4: UZUN VADELİ YABANCI KAYNAKLAR
        // ============================================================
        new AccountSeed("4",    "UZUN VADELİ YABANCI KAYNAKLAR",                      LIABILITY, CREDIT, false),
        new AccountSeed("40",   "Mali Borçlar",                                        LIABILITY, CREDIT, false),
        new AccountSeed("400",  "Banka Kredileri",                                     LIABILITY, CREDIT, true),
        new AccountSeed("405",  "Çıkarılmış Tahviller",                                LIABILITY, CREDIT, true),
        new AccountSeed("42",   "Ticari Borçlar",                                      LIABILITY, CREDIT, false),
        new AccountSeed("420",  "Satıcılar",                                           LIABILITY, CREDIT, true),
        new AccountSeed("421",  "Borç Senetleri",                                      LIABILITY, CREDIT, true),
        new AccountSeed("43",   "Diğer Borçlar",                                       LIABILITY, CREDIT, false),
        new AccountSeed("431",  "Ortaklara Borçlar",                                   LIABILITY, CREDIT, true),
        new AccountSeed("436",  "Diğer Çeşitli Borçlar",                               LIABILITY, CREDIT, true),
        new AccountSeed("44",   "Alınan Avanslar",                                     LIABILITY, CREDIT, false),
        new AccountSeed("440",  "Alınan Sipariş Avansları",                            LIABILITY, CREDIT, true),
        new AccountSeed("47",   "Borç ve Gider Karşılıkları",                          LIABILITY, CREDIT, false),
        new AccountSeed("472",  "Kıdem Tazminatı Karşılığı",                           LIABILITY, CREDIT, true),
        new AccountSeed("479",  "Diğer Borç ve Gider Karşılıkları",                    LIABILITY, CREDIT, true),
        new AccountSeed("48",   "Gelecek Yıllara Ait Gelirler ve Gider Tahakkukları",  LIABILITY, CREDIT, false),
        new AccountSeed("480",  "Gelecek Yıllara Ait Gelirler",                        LIABILITY, CREDIT, true),
        new AccountSeed("481",  "Gider Tahakkukları",                                  LIABILITY, CREDIT, true),
        // ============================================================
        // SINIF 5: ÖZKAYNAKLAR
        // ============================================================
        new AccountSeed("5",    "ÖZKAYNAKLAR",                                         EQUITY, CREDIT, false),
        new AccountSeed("50",   "Ödenmiş Sermaye",                                     EQUITY, CREDIT, false),
        new AccountSeed("500",  "Sermaye",                                             EQUITY, CREDIT, true),
        new AccountSeed("501",  "Ödenmemiş Sermaye (-)",                               EQUITY, DEBIT, true),
        new AccountSeed("52",   "Sermaye Yedekleri",                                   EQUITY, CREDIT, false),
        new AccountSeed("520",  "Hisse Senedi İhraç Primleri",                         EQUITY, CREDIT, true),
        new AccountSeed("521",  "Hisse Senedi İptal Kârları",                          EQUITY, CREDIT, true),
        new AccountSeed("522",  "M.D.V. Yeniden Değerleme Artışları",                  EQUITY, CREDIT, true),
        new AccountSeed("529",  "Diğer Sermaye Yedekleri",                             EQUITY, CREDIT, true),
        new AccountSeed("54",   "Kâr Yedekleri",                                      EQUITY, CREDIT, false),
        new AccountSeed("540",  "Yasal Yedekler",                                      EQUITY, CREDIT, true),
        new AccountSeed("541",  "Statü Yedekleri",                                     EQUITY, CREDIT, true),
        new AccountSeed("542",  "Olağanüstü Yedekler",                                 EQUITY, CREDIT, true),
        new AccountSeed("549",  "Diğer Kâr Yedekleri",                                EQUITY, CREDIT, true),
        new AccountSeed("57",   "Geçmiş Yıllar Kârları",                               EQUITY, CREDIT, false),
        new AccountSeed("570",  "Geçmiş Yıllar Kârları",                               EQUITY, CREDIT, true),
        new AccountSeed("58",   "Geçmiş Yıllar Zararları (-)",                         EQUITY, DEBIT, false),
        new AccountSeed("580",  "Geçmiş Yıllar Zararları (-)",                         EQUITY, DEBIT, true),
        new AccountSeed("59",   "Dönem Net Kârı (Zararı)",                             EQUITY, CREDIT, false),
        new AccountSeed("590",  "Dönem Net Kârı",                                      EQUITY, CREDIT, true),
        new AccountSeed("591",  "Dönem Net Zararı (-)",                                EQUITY, DEBIT, true),
        // ============================================================
        // SINIF 6: GELİR TABLOSU HESAPLARI
        // ============================================================
        new AccountSeed("6",    "GELİR TABLOSU HESAPLARI",                             REVENUE, CREDIT, false),
        new AccountSeed("60",   "Brüt Satışlar",                                       REVENUE, CREDIT, false),
        new AccountSeed("600",  "Yurt İçi Satışlar",                                   REVENUE, CREDIT, true),
        new AccountSeed("601",  "Yurt Dışı Satışlar",                                  REVENUE, CREDIT, true),
        new AccountSeed("602",  "Diğer Gelirler",                                      REVENUE, CREDIT, true),
        new AccountSeed("61",   "Satış İndirimleri (-)",                               REVENUE, DEBIT, false),
        new AccountSeed("610",  "Satıştan İadeler (-)",                                REVENUE, DEBIT, true),
        new AccountSeed("611",  "Satış İskontoları (-)",                               REVENUE, DEBIT, true),
        new AccountSeed("612",  "Diğer İndirimler (-)",                                REVENUE, DEBIT, true),
        new AccountSeed("62",   "Satışların Maliyeti (-)",                             EXPENSE, DEBIT, false),
        new AccountSeed("620",  "Satılan Mamuller Maliyeti (-)",                       EXPENSE, DEBIT, true),
        new AccountSeed("621",  "Satılan Ticari Mallar Maliyeti (-)",                  EXPENSE, DEBIT, true),
        new AccountSeed("622",  "Satılan Hizmet Maliyeti (-)",                         EXPENSE, DEBIT, true),
        new AccountSeed("623",  "Diğer Satışların Maliyeti (-)",                       EXPENSE, DEBIT, true),
        new AccountSeed("63",   "Faaliyet Giderleri (-)",                              EXPENSE, DEBIT, false),
        new AccountSeed("630",  "Araştırma ve Geliştirme Giderleri (-)",               EXPENSE, DEBIT, true),
        new AccountSeed("631",  "Pazarlama Satış ve Dağıtım Giderleri (-)",            EXPENSE, DEBIT, true),
        new AccountSeed("632",  "Genel Yönetim Giderleri (-)",                         EXPENSE, DEBIT, true),
        new AccountSeed("64",   "Diğer Faaliyetlerden Olağan Gelir ve Kârlar",         REVENUE, CREDIT, false),
        new AccountSeed("640",  "İştiraklerden Temettü Gelirleri",                     REVENUE, CREDIT, true),
        new AccountSeed("641",  "Bağlı Ortaklıklardan Temettü Gelirleri",              REVENUE, CREDIT, true),
        new AccountSeed("642",  "Faiz Gelirleri",                                      REVENUE, CREDIT, true),
        new AccountSeed("643",  "Komisyon Gelirleri",                                  REVENUE, CREDIT, true),
        new AccountSeed("644",  "Konusu Kalmayan Karşılıklar",                         REVENUE, CREDIT, true),
        new AccountSeed("645",  "Menkul Kıymet Satış Kârları",                         REVENUE, CREDIT, true),
        new AccountSeed("646",  "Kambiyo Kârları",                                     REVENUE, CREDIT, true),
        new AccountSeed("647",  "Reeskont Faiz Gelirleri",                             REVENUE, CREDIT, true),
        new AccountSeed("649",  "Diğer Olağan Gelir ve Kârlar",                        REVENUE, CREDIT, true),
        new AccountSeed("65",   "Diğer Faaliyetlerden Olağan Gider ve Zararlar (-)",   EXPENSE, DEBIT, false),
        new AccountSeed("653",  "Komisyon Giderleri (-)",                               EXPENSE, DEBIT, true),
        new AccountSeed("654",  "Karşılık Giderleri (-)",                               EXPENSE, DEBIT, true),
        new AccountSeed("655",  "Menkul Kıymet Satış Zararları (-)",                   EXPENSE, DEBIT, true),
        new AccountSeed("656",  "Kambiyo Zararları (-)",                                EXPENSE, DEBIT, true),
        new AccountSeed("657",  "Reeskont Faiz Giderleri (-)",                          EXPENSE, DEBIT, true),
        new AccountSeed("659",  "Diğer Olağan Gider ve Zararlar (-)",                   EXPENSE, DEBIT, true),
        new AccountSeed("66",   "Finansman Giderleri (-)",                             EXPENSE, DEBIT, false),
        new AccountSeed("660",  "Kısa Vadeli Borçlanma Giderleri (-)",                 EXPENSE, DEBIT, true),
        new AccountSeed("661",  "Uzun Vadeli Borçlanma Giderleri (-)",                 EXPENSE, DEBIT, true),
        new AccountSeed("67",   "Olağan Dışı Gelir ve Kârlar",                         REVENUE, CREDIT, false),
        new AccountSeed("671",  "Önceki Dönem Gelir ve Kârları",                       REVENUE, CREDIT, true),
        new AccountSeed("679",  "Diğer Olağan Dışı Gelir ve Kârlar",                   REVENUE, CREDIT, true),
        new AccountSeed("68",   "Olağan Dışı Gider ve Zararlar (-)",                   EXPENSE, DEBIT, false),
        new AccountSeed("681",  "Önceki Dönem Gider ve Zararları (-)",                  EXPENSE, DEBIT, true),
        new AccountSeed("689",  "Diğer Olağan Dışı Gider ve Zararlar (-)",              EXPENSE, DEBIT, true),
        new AccountSeed("69",   "Dönem Net Kârı veya Zararı",                          EQUITY, CREDIT, false),
        new AccountSeed("690",  "Dönem Kârı veya Zararı",                              EQUITY, CREDIT, true),
        new AccountSeed("691",  "Dönem Kârı Vergi ve Diğer Yasal Yükümlülük Karşılıkları (-)", EQUITY, DEBIT, true),
        new AccountSeed("692",  "Dönem Net Kârı veya Zararı",                          EQUITY, CREDIT, true),
        // ============================================================
        // SINIF 7: MALİYET HESAPLARI
        // ============================================================
        new AccountSeed("7",    "MALİYET HESAPLARI",                                   COST, DEBIT, false),
        new AccountSeed("70",   "Maliyet Muhasebesi Bağlantı Hesabı",                  COST, DEBIT, false),
        new AccountSeed("700",  "Maliyet Muhasebesi Bağlantı Hesabı",                  COST, DEBIT, true),
        new AccountSeed("701",  "Maliyet Muhasebesi Yansıtma Hesabı",                  COST, CREDIT, true),
        new AccountSeed("71",   "Direkt İlk Madde ve Malzeme Giderleri",               COST, DEBIT, false),
        new AccountSeed("710",  "Direkt İlk Madde ve Malzeme Giderleri",               COST, DEBIT, true),
        new AccountSeed("711",  "Direkt İlk Madde ve Malzeme Giderleri Yansıtma (-)", COST, CREDIT, true),
        new AccountSeed("713",  "Direkt İlk Madde ve Malzeme Fiyat Farkları",          COST, DEBIT, true),
        new AccountSeed("72",   "Direkt İşçilik Giderleri",                            COST, DEBIT, false),
        new AccountSeed("720",  "Direkt İşçilik Giderleri",                            COST, DEBIT, true),
        new AccountSeed("721",  "Direkt İşçilik Giderleri Yansıtma (-)",               COST, CREDIT, true),
        new AccountSeed("73",   "Genel Üretim Giderleri",                              COST, DEBIT, false),
        new AccountSeed("730",  "Genel Üretim Giderleri",                              COST, DEBIT, true),
        new AccountSeed("731",  "Genel Üretim Giderleri Yansıtma (-)",                 COST, CREDIT, true),
        new AccountSeed("74",   "Hizmet Üretim Maliyeti",                              COST, DEBIT, false),
        new AccountSeed("740",  "Hizmet Üretim Maliyeti",                              COST, DEBIT, true),
        new AccountSeed("741",  "Hizmet Üretim Maliyeti Yansıtma (-)",                 COST, CREDIT, true),
        new AccountSeed("75",   "Araştırma ve Geliştirme Giderleri",                   COST, DEBIT, false),
        new AccountSeed("750",  "Araştırma ve Geliştirme Giderleri",                   COST, DEBIT, true),
        new AccountSeed("751",  "Araştırma ve Geliştirme Giderleri Yansıtma (-)",      COST, CREDIT, true),
        new AccountSeed("76",   "Pazarlama Satış ve Dağıtım Giderleri",                COST, DEBIT, false),
        new AccountSeed("760",  "Pazarlama Satış ve Dağıtım Giderleri",                COST, DEBIT, true),
        new AccountSeed("761",  "Pazarlama Satış ve Dağıtım Giderleri Yansıtma (-)",   COST, CREDIT, true),
        new AccountSeed("77",   "Genel Yönetim Giderleri",                             COST, DEBIT, false),
        new AccountSeed("770",  "Genel Yönetim Giderleri",                             COST, DEBIT, true),
        new AccountSeed("771",  "Genel Yönetim Giderleri Yansıtma (-)",                COST, CREDIT, true),
        new AccountSeed("78",   "Finansman Giderleri",                                 COST, DEBIT, false),
        new AccountSeed("780",  "Finansman Giderleri",                                 COST, DEBIT, true),
        new AccountSeed("781",  "Finansman Giderleri Yansıtma (-)",                    COST, CREDIT, true),
        new AccountSeed("79",   "Gider Çeşitleri",                                     COST, DEBIT, false),
        new AccountSeed("790",  "İlk Madde ve Malzeme Giderleri",                      COST, DEBIT, true),
        new AccountSeed("791",  "İşçi Ücret ve Giderleri",                             COST, DEBIT, true),
        new AccountSeed("792",  "Memur Ücret ve Giderleri",                            COST, DEBIT, true),
        new AccountSeed("793",  "Dışarıdan Sağlanan Fayda ve Hizmetler",               COST, DEBIT, true),
        new AccountSeed("794",  "Çeşitli Giderler",                                    COST, DEBIT, true),
        new AccountSeed("795",  "Vergi Resim ve Harçlar",                              COST, DEBIT, true),
        new AccountSeed("796",  "Amortisman ve Tükenme Payları",                       COST, DEBIT, true),
        new AccountSeed("797",  "Finansman Giderleri",                                 COST, DEBIT, true),
        // ============================================================
        // SINIF 9: NAZIM HESAPLAR
        // ============================================================
        new AccountSeed("9",    "NAZIM HESAPLAR",                                      ASSET, DEBIT, false),
        new AccountSeed("90",   "Nazım Hesaplar",                                      ASSET, DEBIT, false),
        new AccountSeed("900",  "Taahhüt Giderleri",                                  ASSET, DEBIT, true),
        new AccountSeed("901",  "Taahhüt Giderleri Karşılığı",                        ASSET, CREDIT, true),
        new AccountSeed("91",   "Alınan Teminat Mektupları",                            ASSET, DEBIT, false),
        new AccountSeed("910",  "Alınan Teminat Mektupları",                           ASSET, DEBIT, true),
        new AccountSeed("911",  "Alınan Teminat Mektupları Karşılığı",                 ASSET, CREDIT, true),
        new AccountSeed("92",   "Kredi Kartları",                                      ASSET, DEBIT, false),
        new AccountSeed("920",  "Kredi Kartları",                                      ASSET, DEBIT, true),
        new AccountSeed("921",  "Kredi Kartları Karşılığı",                            ASSET, CREDIT, true),
        new AccountSeed("95",   "Leasing Taahhütleri",                                 ASSET, DEBIT, false),
        new AccountSeed("950",  "Leasing Taahhütleri",                                 ASSET, DEBIT, true),
        new AccountSeed("951",  "Leasing Taahhütleri Karşılığı",                       ASSET, CREDIT, true),
        new AccountSeed("96",   "Kefalet Taahhütleri",                                 ASSET, DEBIT, false),
        new AccountSeed("960",  "Kefalet Taahhütleri",                                 ASSET, DEBIT, true),
        new AccountSeed("961",  "Kefalet Taahhütleri Karşılığı",                       ASSET, CREDIT, true)
    );

    public void seedIntoplan(AccountPlan plan) {
        // Sort by code length (ascending) so parents are created before children
        List<AccountSeed> sorted = TDHP_ACCOUNTS.stream()
                .sorted((a, b) -> Integer.compare(a.code().length(), b.code().length()))
                .toList();

        Map<String, ChartAccount> savedByCode = new HashMap<>();

        for (AccountSeed seed : sorted) {
            ChartAccount account = new ChartAccount();
            account.setPlan(plan);
            account.setCode(seed.code());
            account.setName(seed.name());
            account.setAccountType(seed.type());
            account.setNormalBalance(seed.balance());
            account.setIsDetail(seed.isDetail());
            account.setIsActive(true);
            account.setIsBlocked(false);
            account.setLevel(computeLevel(seed.code()));

            // Link to parent
            ChartAccount parent = findParent(seed.code(), savedByCode);
            account.setParent(parent);

            ChartAccount saved = chartAccountRepository.save(account);
            savedByCode.put(seed.code(), saved);
        }

        log.info("TDHP seed complete: {} accounts loaded into plan [{}]", sorted.size(), plan.getCode());
    }

    private int computeLevel(String code) {
        int len = code.length();
        if (len == 1) return 1;
        if (len == 2) return 2;
        if (len == 3) return 3;
        if (len <= 5) return 4;
        return 5;
    }

    private ChartAccount findParent(String code, Map<String, ChartAccount> savedByCode) {
        // Try progressively shorter prefixes
        for (int i = code.length() - 1; i >= 1; i--) {
            String parentCode = code.substring(0, i);
            ChartAccount parent = savedByCode.get(parentCode);
            if (parent != null) return parent;
        }
        return null;
    }
}
