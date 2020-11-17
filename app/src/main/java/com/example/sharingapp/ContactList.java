package com.example.sharingapp;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ContactList {

    private ArrayList<Contact> contacts;

    private String FILENAME;

    public ContactList() {
        this.contacts = new ArrayList<>();
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public String getFILENAME() {
        return FILENAME;
    }

    public void setFILENAME(String FILENAME) {
        this.FILENAME = FILENAME;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<String> getAllUsernames() {
        return this.contacts.stream().map(Contact::getUsername).collect(Collectors.toList());
    }
    public void addContact(Contact contact){
        this.contacts.add(contact);
    }
    public void deleteContact(Contact contact) {
        this.contacts.remove(contact);
    }
    public Contact getContact(int index) {
        return this.contacts.get(index);
    }

    public int getSize(){
        return this.contacts.size();
    }
    public int getIndex(Contact contact){
        return this.contacts.indexOf(contact);
    }
    public boolean hasContact(Contact contact) {
        return this.contacts.contains(contact);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Contact getContactByUsername(String username){
        return this.contacts.stream().filter((x)->(x.getUsername().equals(username))).findFirst().get();
    }
    public void loadContacts(Context context){
        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Contact>>() {}.getType();
            contacts = gson.fromJson(isr, listType);
            fis.close();
        } catch (FileNotFoundException e) {
            contacts = new ArrayList<>();
        } catch (IOException e) {
            contacts = new ArrayList<>();
        }
    }

    public void saveContacts(Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(contacts, osw);
            osw.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean isUsernameAvailable(String username_str) {
        return this.contacts.stream().map(Contact::getUsername).collect(Collectors.toList()).contains(username_str);
    }
}
