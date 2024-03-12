package ex;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteHighlightTest {

    @Test
    void highlight() {
        /**
         * abc -> abc
         * note -> {note}
         * 1 note -> 1 {note}
         * 1 note 2 -> 1 {note} 2
         * keynote -> keynote
         * ke1note -> ke1note
         * yes note1 -> yes note1
         * yes notea -> yes notea
         * no a note -> no a {note}
         */
        assertThat(highlight("abc")).isEqualTo("abc");
        assertThat(highlight("efg")).isEqualTo("efg");
        assertThat(highlight("note")).isEqualTo("{note}");
        assertThat(highlight("1 note")).isEqualTo("1 {note}");
        assertThat(highlight("1 note 2")).isEqualTo("1 {note} 2");
        assertThat(highlight("keynote")).isEqualTo("keynote");
        assertThat(highlight("ke1note")).isEqualTo("ke1note");
        assertThat(highlight("ke4note")).isEqualTo("ke4note");
        assertThat(highlight("keanote")).isEqualTo("keanote");
        assertThat(highlight("yes note1")).isEqualTo("yes note1");
        assertThat(highlight("yes notea")).isEqualTo("yes notea");
        assertThat(highlight("no a note")).isEqualTo("no a {note}");
        assertThat(highlight("no a note note")).isEqualTo("no a {note} {note}");
        assertThat(highlight("no a note anote")).isEqualTo("no a {note} anote");
        assertThat(highlight("no a note anote")).isEqualTo("no a {note} anote");
        assertThat(highlight("no a note anote note")).isEqualTo("no a {note} anote {note}");
        assertThat(highlight("no a note anote note 11")).isEqualTo("no a {note} anote {note} 11");
    }

    private String highlight(String str) {
        String result = "";
        while (true) {
            int idx = str.indexOf("note");
            if (idx == -1) {
                result += str;
                break;
            }
            if (isPrechNotSpace(str, idx) || isPostchNotSpace(str, idx)) {
                result += str.substring(0, idx + "note".length());
                str = str.substring(idx + "note".length());
            } else {
                String preStr = idx > 0 ? str.substring(0, idx) : "";
                result += preStr + "{note}";
                str = str.substring(idx + "note".length());
            }
        }
        return result;
//        return str.replace("note", "{note}");
    }

    private boolean isPrechNotSpace(String str, int idx) {
        int prechIdx = idx - 1;
        return prechIdx >= 0 && isNotSpace(str.charAt(prechIdx));
    }

    private boolean isPostchNotSpace(String str, int idx) {
        int postchIdx = idx + "note".length();
        return postchIdx < str.length() && isNotSpace(str.charAt(postchIdx));
    }

    private boolean isNotSpace(char ch) {
        return (ch >= 'a' && ch <= 'y') || (ch >= '0' && ch <= '9');
    }
}
