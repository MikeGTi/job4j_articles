1. Настройка БД Hsqldb - таблица 'articles' переведена из режима in memory в режим cached (для сброса данных из опер. памяти на диск, delay 10 sec);
2. Поля типа String, в моделях, принудительно переведены в 'String pool', методом String.intern();
3. RandomArticleGenerator, метод generate() - убрано излишнее копирование массива слов;
4. SimpleArticleService, метод generate() - добавлена очистка массивов words, articles;