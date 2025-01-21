## Проект "Симуляция"
Пошаговая симуляция 2D мира, населённого травоядными и хищниками. Кроме существ, мир содержит ресурсы (траву), которым питаются травоядные, 
и статичные объекты (камни и мухоморы), с которыми нельзя взаимодействовать - они просто занимают место.

## Правила игры
1. У кроликов и волков есть запас здоровья. Кролики имеют на старте игры 5 хп, волки 3 хп.
2. Кролики ищут траву, сближаясь с ней – мгновенно съедают. Когда кролик съедает траву он получает +5 хп.
3. Волки ищут зайцев, сближаясь с ними – мгновенно съедают. Когда волк съедает кролика он получает +2 хп.
4. Волк имеет скорость (speed = 2). Это позволяет за один ход (Turn) сделать два действия.
5. Каждый ход, волки и зайцы теряют по одному хп. Если хп = 0 существо умирает и удалятся из карты.
6. Каждые 3 хода на карту добавляется новая трава, каждые 4 - новый заяц, каждые 10 - новый волк.
7. Игра завершается после гибели всех кроликов или волков.

## Особенности игры
У вас есть выбор:
- запустить симуляцию в бесконечном режиме до тех пора волки или зайцы не одержут победут.
- делать один ход.
Кроме того вы можете поставить симуляцию на паузу.

### Старт игры
![StartGame](https://github.com/ProgWrite/Simulation/blob/master/Начало%20игры.PNG)
