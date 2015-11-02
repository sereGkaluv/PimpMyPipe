package indsys.filter;

import indsys.entity.Word;
import indsys.entity.WordList;
import thirdparty.filter.DataEnrichmentFilter;
import thirdparty.interfaces.Readable;
import thirdparty.interfaces.Writable;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sereGkaluv on 01-Nov-15.
 */
public class WordShiftFilter extends DataEnrichmentFilter<WordList, List<WordList>> {

    public WordShiftFilter(Readable<WordList> input, Writable<List<WordList>> output)
    throws InvalidParameterException {
        super(input, output);
    }

    @Override
    protected boolean fillEntity(WordList wordList, List<WordList> entity) {
        if (wordList != null) {
            int id = wordList.getId();
            LinkedList<Word> tempList = new LinkedList<>(wordList.getValue());

            if (tempList.isEmpty()) {
                return true;
            }

            entity.add(id, wordList);

            for (int shift = 0; shift < tempList.size() - 1; ++shift) {
                Word tempWord = tempList.removeFirst();
                tempList.addLast(tempWord);

                entity.add(listToWordList(id, tempList));
            }
        }

        return false;
    }

    @Override
    protected List<WordList> getNewEntityObject() {
        return new LinkedList<>();
    }

    private WordList listToWordList(int id, List<Word> list) {
        return new WordList(id, new LinkedList<>(list));
    }
}