package com.acceler8tion.Saturn.database;

import com.acceler8tion.Saturn.util.FileFormat;
import com.acceler8tion.Saturn.util.FileStream;

import java.io.IOException;
import java.util.function.Function;

public interface IDB<T> {

    T init();

    T getDB();

    void setDB(T db);

    String getPath();

    FileFormat format();

    String convert();

    default <E> E extract(Function<T, E> extractor){
        try {
            return extractor.apply(getDB());
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    default void update(Function<T, T> updater){
        T t;
        try {
            t = updater.apply(getDB());
            setDB(t);
            sync();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    default void sync(){
        try {
            FileFormat fm = format();
            FileStream.write(getPath() + fm.getForm(), getDB());
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
