# Project Nangdia (formerly Pilem) - Product Roadmap & Backlog

> Daftar pekerjaan, fitur baru, dan perbaikan untuk fase pengembangan aplikasi selanjutnya. Diambil dan dikurasi dari catatan fitur terbaru.

## 🚀 Fase 1: Perbaikan Kritis & Penyesuaian UI (Quick Wins)

- [x] **Ganti Nama Aplikasi**: Ubah nama aplikasi dari "Pilem" menjadi "Nangdia" di `strings.xml` dan konfigurasi Gradle.
- [x] **Navigasi Bar**: Perkecil padding atau margin pada area bawah navigasi agar tidak terlalu memakan layar (tingginya lebih efisien).
- [x] **Data Parental Guide**: Tangani masalah informasi *Parental Guide* yang tidak muncul menyeluruh untuk beberapa film/seri, sesuaikan *fallback* atau cari *endpoint* data tambahan.

## 🎬 Fase 2: Peningkatan Halaman Detail & Sumber (Metadata)

- [x] **Integrasi Link Eksternal**:
  - [x] Tambahkan menu overflow di halaman detail: "Buka di MDBList".
  - [x] Tambahkan tautan/link langsung ke halaman *Rotten Tomatoes* saat popup skor Tomato dan Popcorn diklik oleh pengguna.
- [x] **Jadwal Streaming**: Tambahkan informasi tanggal rilis "Kapan Streaming?" khusus untuk kategori film yang saat ini sedang tayang di bioskop.
- [x] **Penghargaan (Awards) Part 1**: Tampilkan informasi peraihan penghargaan apa saja yang dimenangkan di halaman detail film.
- [x] **Rekomendasi**: Tambahkan bagian horizontally scrollable **"More Like This"** di bagian terbawah halaman detail.

## 👨‍👩‍👧‍👦 Fase 3: Pencarian, Person (Aktor & Kru), & UI List

- [x] **Rating di Home Screen**: Tampilkan indikator skor/rating untuk setiap *card* film pada halaman *Home* (posisikan bersebelahan dengan tahun rilis).
- [ ] **Pemeran & Kru (Persons)**:
  - [x] Buat fitur kapabilitas "Persons" penuh (Klik nama aktor untuk melihat filmografinya).
  - [x] Munculkan daftar pemeran utama ringkas di *Search Results* tanpa mengorbankan desain *compact*.
  - [x] Tampilkan deretan *Persons* (aktor/kru) yang terkait langsung di dalam detail sebuah film.

## 🗓 Fase 4: Fitur Lanjutan (Series, List & Advanced Search)

- [x] **Eksplorasi Series**: Tambahkan rincian per episode dan per season untuk sebuah TV Series.
- [x] **Kalender Tayang**: Buat fitur halaman "Kalender" interaktif untuk melihat jadwal rilis film dan episode mendatang.
- [x] **Pencarian Lanjut**: Implementasikan *Advanced Search* (Filter berdasarkan genre, rating, tahun, dll).
- [x] **Halaman List**: Tambahkan ekosistem *Lists* (misal: fitur membuat *custom list* atau melihat daftar kurasi *trending*).

## 🏆 Fase 5: Halaman Khusus & Profil

- [x] **Indeks Penghargaan**: Buat tab/halaman khusus "Awards" untuk memantau musim penghargaan film (Oscar, Emmy, dll) lengkap dengan daftarnya dan siapa saja pemenangnya per tahun.
- [x] **Akun & Pengaturan**: Tambahkan halaman profil, opsi kustomisasi UI, *dark mode toggle*, atau manajemen akun (jika terhubung ke *cloud* / sinkronisasi Trakt/TMDB).

---
