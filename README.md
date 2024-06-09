# Workout Recommendation Using KNN

## Demo

https://github.com/aviedb/project-ai-android/assets/22285186/8551b087-438c-45e3-8cd6-c07d30a76f51

## Flowchart

![Flowchart input output](https://github.com/aviedb/project-ai-android/assets/22285186/b38fac84-3c70-4485-8c22-5c25d9e41d3a)

## Implementasi

Aplikasi ini terdiri dari dua fragment utama:
1. **InputFragment**: Fragment ini memungkinkan pengguna untuk memasukkan informasi tentang bagian tubuh yang ingin dilatih, tingkat kesulitan, dan ketersediaan peralatan.
2. **RecommendationFragment**: Fragment ini menampilkan daftar rekomendasi latihan yang dihasilkan berdasarkan input pengguna.

## Alur Proses

![Interaksi dalam activity](https://github.com/aviedb/project-ai-android/assets/22285186/9d732674-e284-4559-8fa3-a86025ed1cd9)

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

![Flow algoritme KNN](https://github.com/aviedb/project-ai-android/assets/22285186/4dfde028-ce60-4c28-bc42-d122a5c1c05c)

1. **Inisialisasi dan Pelatihan Model**:
   - Menginisialisasi objek KNN dengan nilai k (jumlah tetangga terdekat yang akan dipertimbangkan).
   - Melatih model KNN dengan dataset latihan yang diberikan.

2. **Perhitungan Jarak**:
   - Menghitung jarak Euclidean antara dua objek latihan berdasarkan fitur numeriknya (tipe, bagian tubuh, alat, dan level).

3. **Rekomendasi**:
   - Menghitung jarak antara `userLatihan` dengan setiap latihan dalam dataset.
   - Mengurutkan latihan berdasarkan jarak terdekat dan mengambil k latihan terdekat sebagai rekomendasi.

## Dataset

Dataset yang digunakan untuk pelatihan model diambil dari platform [kaggle.com](https://www.kaggle.com/datasets/niharika41298/gym-exercise-data).
