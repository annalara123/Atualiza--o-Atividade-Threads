public class Calibration {
    public static int value(String linha) {
        Character primeiro = null;
        Character ultimo = null;

        for (char c : linha.toCharArray()) {
            if (Character.isDigit(c)) {
                if (primeiro == null) {
                    primeiro = c;
                }
                ultimo = c;
            }
        }

        if (primeiro == null && ultimo == null) {
            return 0;
        }

        if (primeiro != null) {
            return Integer.parseInt(String.valueOf(primeiro));
        }

        return Integer.parseInt(String.valueOf(primeiro) + String.valueOf(ultimo));
    }
}
