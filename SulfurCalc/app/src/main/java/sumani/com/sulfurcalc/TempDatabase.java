package sumani.com.sulfurcalc;

/**
 * Created by Андрей on 17.05.2016.
 */
public class TempDatabase extends Object {

    public class SoilType extends Object {
            public int id;
            public String name;
            public float low;
            public float high;

        public SoilType(int id, String name, float low, float high) {
            this.id = id;
            this.name = name;
            this.low = low;
            this.high = high;
        }

        @Override
        public String toString() {
            return name;
        }
    }


    public class Time extends Object {

        public int id;
        public String name;
        public float quantity;
        public Time(int id, String name, float quantity) {
                this.id = id;
                this.name = name;
                this.quantity = quantity;
            }

            @Override
            public String toString() {
                return name;
            }

        }
        public class Soil extends Object {
            public int id;
            public String name;
            public float quantity;
            public Soil(int id, String name, float quantity) {
                this.id = id;
                this.name = name;
                this.quantity = quantity;
            }

            @Override
            public String toString() {
                return name;
            }
        }
        public class Culture extends Object {
            public int id;
            public String name;
            public float quantity;
            public Culture(int id, String name, float quantity) {
                this.id = id;
                this.name = name;
                this.quantity = quantity;
            }

            @Override
            public String toString() {
                return name;
            }
        }
        public class Region extends Object {
            public int id;
            public String name;
            public float quantity;
            public Region(int id, String name, float quantity) {
                this.id = id;
                this.name = name;
                this.quantity = quantity;
            }

            @Override
            public String toString() {
                return name;
            }
        }

        public class Fertilizer extends Object {
            public int id;
            public String name;
            public float quantity;
            public Fertilizer(int id, String name, float quantity) {
                this.id = id;
                this.name = name;
                this.quantity = quantity;
            }

            @Override
            public String toString() {
                return name;
            }
        }

            public Region[] regions;
            public Fertilizer[] fertilizers;
            public Culture[] cultures;
            public Soil[] soils;
            public Time[] times;
            public SoilType[] soiltypes;
            public TempDatabase() {
                regions = new Region[10];
                regions[0] = new Region(1, "Северо-западный", 20.8F);
                regions[1] = new Region(2, "Центральный", 13.1F);
                regions[2] = new Region(3, "Волго-Вятский", 8.9F);
                regions[3] = new Region(4, "Центрально-Черноземный", 6.6F);
                regions[4] = new Region(5, "Поволжский", 8.4F);
                regions[5] = new Region(6, "Северо-Кавказский", 8.6F);
                regions[6] = new Region(7, "Уральский", 11.2F);
                regions[7] = new Region(8, "Западно-Сибирский", 21.6F);
                regions[8] = new Region(9, "Восточно-Сибирский", 0.2F);
                regions[9] = new Region(10, "Дальневосточный", 0.9F);
                //----------------------------
                fertilizers = new Fertilizer[22];
                fertilizers[0] = new Fertilizer(1, "Сульфат аммония", 24.0F);
                fertilizers[1] = new Fertilizer(2, "Сульфат аммония натрия ", 29.0F);
                fertilizers[2] = new Fertilizer(3, "Сульфат железа", 11.5F);
                fertilizers[3] = new Fertilizer(4, "Сульфат магния", 18.6F);
                fertilizers[4] = new Fertilizer(5, "Сульфат меди", 12.8F);
                fertilizers[5] = new Fertilizer(6, "Cульфат цинка ", 17.8F);
                fertilizers[6] = new Fertilizer(7, "Сульфат марганца", 15.0F);
                fertilizers[7] = new Fertilizer(8, "Сульфат натрия (эпсомит)", 12.8F);
                fertilizers[8] = new Fertilizer(9, "Сульфат калия", 18.0F);
                fertilizers[9] = new Fertilizer(10, "Элементарная сера", 100.0F);
                fertilizers[10] = new Fertilizer(11, "Каинит", 13.0F);
                fertilizers[11] = new Fertilizer(12, "Щенит", 15.9F);
                fertilizers[12] = new Fertilizer(13, "Суперфосфат двойной", 0.5F);
                fertilizers[13] = new Fertilizer(14, "Суперфосфат простой", 12.0F);
                fertilizers[14] = new Fertilizer(15, "Суперфос", 0.9F);
                fertilizers[15] = new Fertilizer(16, "Фосфогипс", 18.3F);
                fertilizers[16] = new Fertilizer(17, "Сланцевая зола", 13.5F);
                fertilizers[17] = new Fertilizer(18, "Известково-серные отходы", 2.3F);
                fertilizers[18] = new Fertilizer(19, "Фосфатшлаки", 5.0F);
                fertilizers[19] = new Fertilizer(20, "Доломиты", 0.15F);
                fertilizers[20] = new Fertilizer(21, "Сыромолотый гипс", 0.03F);
                fertilizers[21] = new Fertilizer(22, "Калимагнезия", 15.8F);
                //----------------------------
                cultures = new Culture[15];
                cultures[0] = new Culture(1,"Озимая пшеница", 3.1F);
                cultures[1] = new Culture(2, "Ячмень ", 3.6F);
                cultures[2] = new Culture(3, "Овес", 1.8F);
                cultures[3] = new Culture(4, "Кукуруза на зерно", 2.2F);
                cultures[4] = new Culture(5, "Горох ", 9.5F);
                cultures[5] = new Culture(6, "Люцерна ", 2.3F);
                cultures[6] = new Culture(7, "Клевер ", 3.2F);
                cultures[7] = new Culture(8, "Сахарная свекла", 0.8F);
                cultures[8] = new Culture(9, "Столовая свекла", 0.8F);
                cultures[9] = new Culture(10, "Морковь", 0.8F);
                cultures[10] = new Culture(11, "Картофель", 0.8F);
                cultures[11] = new Culture(12, "Лук", 1.3F);
                cultures[12] = new Culture(13, "Капуста", 1.2F);
                cultures[13] = new Culture(14, "Турнепс", 1.5F);
                cultures[14] = new Culture(15, "Брюква", 1.8F);
                //--------------------
                soils = new Soil[4];
                soils[0] = new Soil(1, "Песчаные и супесчаные почвы", 1.5F);
                soils[1] = new Soil(2, "Легкосуглинистые ", 1.0F);
                soils[2] = new Soil(3, "Среднесуглинистые ", 1.2F);
                soils[3] = new Soil(4, "Тяжелосуглинистые и глинистые", 1.2F);
                //----------------------
                times = new Time[3];
                times[0] = new Time(1, "Не вносяться", 0.0F);
                times[1] = new Time(2, "Под предшественник",0.8F);
                times[2] = new Time(3, "Под текущий год", 1.0F);
                //----------------
                soiltypes= new SoilType[8];
                soiltypes[0] = new SoilType(1, "Дерново-подзолистая", 6, 12);
                soiltypes[1] = new SoilType(2, "Бурая лесная", 6, 12);
                soiltypes[2] = new SoilType(3, "Серая лесная", 6, 12);
                soiltypes[3] = new SoilType(4, "Чернозем выщелоченный", 12, 20);
                soiltypes[4] = new SoilType(5, "Чернозем оподзоленный", 12, 20);
                soiltypes[5] = new SoilType(6, "Чернозем типичный", 12, 20);
                soiltypes[6] = new SoilType(7, "Чернозем обыкновенный", 12, 20);
                soiltypes[7] = new SoilType(8, "Чернозем южный", 12, 20);
            }
}



