import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {

  public static void main(String[] args) throws IOException {
    Animal[] animalM1 = {
            new Animal("cat"),
            new Animal("Dog"),
            new Animal("Elephant"),
            new Animal("Cock"),
            new Animal("Bull"),
            new Animal("Ant"),
            new Animal("Tentecles"),
            new Animal("Worm")
            };
    ByteArrayOutputStream bai = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bai);
    oos.writeInt(animalM1.length);
    for (int i = 0; i < animalM1.length; i++) {
      oos.writeObject(animalM1[i]);
    }
    oos.flush();
    oos.close();
    Animal[] animalM2 = deserializeAnimalArray(bai.toByteArray());
    for (int i = 1; i < animalM2.length; i++) {
      System.out.printf("%d. %s \n", i + 1, animalM2[i].getName());
    }
  }

  public static <T> void findMinMax(Stream<? extends T> stream,
                                    Comparator<? super T> order,
                                    BiConsumer<? super T, ? super T> minMaxConsumer) {
    List<T> list = stream.sorted(order).collect(Collectors.toList());
    if (list.isEmpty()) {
      minMaxConsumer.accept(null, null);
    } else {
      minMaxConsumer.accept(list.get(0), list.get(list.size() - 1));
    }
  }

  public static Animal[] deserializeAnimalArray(byte[] data) {
    ByteArrayInputStream byteStream1 = new ByteArrayInputStream(data);
    Animal[] animal2;
    try (ObjectInputStream objectInputStream = new ObjectInputStream(byteStream1)) {
      int size = objectInputStream.readInt();
      animal2 = new Animal[size];
      for (int i = 0; i < size; i++) {
        animal2[i] = (Animal) objectInputStream.readObject();
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
    return animal2;
  }
}
