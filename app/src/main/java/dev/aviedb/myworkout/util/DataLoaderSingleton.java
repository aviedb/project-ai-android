package dev.aviedb.myworkout.util;

import android.content.Context;

public class DataLoaderSingleton {
  private static DataLoader instance;

  private DataLoaderSingleton() {}

  public static DataLoader getInstance(Context context) {
    if (instance == null)
      instance = new DataLoader(context.getApplicationContext());

    return instance;
  }
}