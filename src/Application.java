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
    Animal[] animalBasic = {
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
    oos.writeInt(animalBasic.length);
    for (int i = 0; i < animalBasic.length; i++) {
      oos.writeObject(animalBasic[i]);
    }
    oos.flush();
    oos.close();
    Animal[] animal = deserializeAnimalArray(bai.toByteArray());
    for (int i = 1; i < animal.length; i++) {
      System.out.printf("%d. %s \n", i + 1, animal[i].getName());
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
    ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
    Animal[] animal;
    try (ObjectInputStream objectInputStream = new ObjectInputStream(byteStream)) {
      int size = objectInputStream.readInt();
      animal = new Animal[size];
      for (int i = 0; i < size; i++) {
        animal[i] = (Animal) objectInputStream.readObject();
      }
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
    return animal;
  }
}
