package com.example.smartpharmacy;

public class Pharmacy {
    String namePharmacy;
    String address;
    String time_work;
    String medicament;
    String price;

    Pharmacy(String _namePharmacy, String _address, String _time_work, String _medicament, String _price){
        namePharmacy = _namePharmacy;
        address = _address;
        time_work = _time_work;
        medicament = _medicament;
        price = _price;
    }
}
