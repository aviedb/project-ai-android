# Workout Recommendation Using KNN

## Demo

https://github.com/aviedb/project-ai-android/assets/22285186/8551b087-438c-45e3-8cd6-c07d30a76f51

## Flowchart

![Flowchart input output](https://github.com/aviedb/project-ai-android/assets/22285186/9c68f37c-82be-4a36-b51b-ebb4211fcf04)

## Implementasi

Aplikasi ini terdiri dari dua fragment utama:
1. **InputFragment**: Fragment ini memungkinkan pengguna untuk memasukkan informasi tentang bagian tubuh yang ingin dilatih, tingkat kesulitan, dan ketersediaan peralatan.
2. **RecommendationFragment**: Fragment ini menampilkan daftar rekomendasi latihan yang dihasilkan berdasarkan input pengguna.

## Alur Proses

![Interaksi dalam activity](https://github.com/aviedb/project-ai-android/assets/22285186/4436dc29-6b17-4479-a4f7-bdf4e6fe0800)

1. **MainActivity**:
   - Menginisialisasi fragment `InputFragment` dan `RecommendationFragment`.
   - Mengatur listener untuk menerima hasil rekomendasi dari `InputFragment`.
   - Menerima daftar rekomendasi dari `InputFragment` dan meneruskannya ke `RecommendationFragment`.

2. **InputFragment**:
   - Meng-inflate layout, menginisialisasi widget, memuat dataset, dan melatih model KNN.
   - Mengumpulkan data input dari pengguna, memvalidasi input, dan mengubahnya menjadi nilai numerik yang sesuai.
   - Menggunakan model KNN untuk menghasilkan rekomendasi dan mengirim hasilnya ke `MainActivity`.

3. **RecommendationFragment**:
   - Meng-inflate layout, menginisialisasi `RecyclerView`, dan mengatur adapter.
   - Memperbarui data dalam adapter `RecyclerView` dengan daftar rekomendasi baru dan menampilkan hasilnya.

## Penjelasan Algoritma KNN

![Flow algoritme KNN](https://github.com/aviedb/project-ai-android/assets/22285186/fa3e6b79-4f6a-4f58-a14d-e6b4fee664c8)


1. **Inisialisasi dan Pelatihan Model**:
   - Menginisialisasi objek KNN dengan nilai k (jumlah tetangga terdekat yang akan dipertimbangkan).
   - Melatih model KNN dengan dataset latihan yang diberikan.

2. **Perhitungan Jarak**:
   - Menghitung jarak Euclidean antara dua objek latihan berdasarkan fitur numeriknya (tipe, bagian tubuh, alat, dan level).

3. **Rekomendasi**:
   - Menghitung jarak antara `userLatihan` dengan setiap latihan dalam dataset.
   - Mengurutkan latihan berdasarkan jarak terdekat dan mengambil k latihan terdekat sebagai rekomendasi.
