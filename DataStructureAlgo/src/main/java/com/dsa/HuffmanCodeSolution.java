package com.dsa;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import com.dsa.beans.HuffmanNode;
public class HuffmanCodeSolution {

	private static Map<Character, String> charPrefixHashMap = new HashMap<>();
	public static HuffmanNode root;

	public static void main(String[] args) {

		String test = "1 EUREKA By Edgar A. Poe 2 PREFACE. To the few who love me and whom I love—to those who feel rather than to those who think—to the dreamers and those who put faith in dreams as in the only realities—I offer this Book of Truths, not in its character of Truth-Teller, but for the Beauty that abounds in its Truth; constituting it true. To these I present the composition as an Art-Product alone:—let us say as a Romance; or, if I be not urging too lofty a claim, as a Poem. What I here propound is true:—therefore it cannot die:—or if by any means it be now trodden down so that it die, it will “rise again to the Life Everlasting.” Nevertheless it is as a Poem only that I wish this work to be judged after I am dead. E. A. P. 3 EUREKA: AN ESSAY ON THE MATERIAL AND SPIRITUAL UNIVERSE. It is with humility really unassumed—it is with a sentiment even of awe—that I pen the opening sentence of this work: for of all conceivable subjects I approach the reader with the most solemn—the most comprehensive—the most difficult—the most august. What terms shall I find sufficiently simple in their sublimity—sufficiently sublime in their simplicity—for the mere enunciation of my theme? I design to speak of the Physical, Metaphysical and Mathematical—of the Material and Spiritual Universe:—of its Essence, its Origin, its Creation, its Present Condition and its Destiny. I shall be so rash, moreover, as to challenge the conclusions, and thus, in effect, to question the sagacity, of many of the greatest and most justly reverenced of men. In the beginning, let me as distinctly as possible announce—not the theorem which I hope to demonstrate—for, whatever the mathematicians may assert, there is, in this world at least, no such thing as demonstration—but the ruling idea which, throughout this volume, I shall be continually endeavoring to suggest. 4 My general proposition, then, is this:—In the Original Unity of the First Thing lies the Secondary Cause of All Things, with the Germ of their Inevitable Annihilation. In illustration of this idea, I propose to take such a survey of the Universe that the mind may be able really to receive and to perceive an individual impression. He who from the top of Ætna casts his eyes leisurely around, is affected chiefly by the extent and diversity of the scene. Only by a rapid whirling on his heel could he hope to comprehend the panorama in the sublimity of its oneness. But as, on the summit of Ætna, no man has thought of whirling on his heel, so no man has ever taken into his brain the full uniqueness of the prospect; and so, again, whatever considerations lie involved in this uniqueness, have as yet no practical existence for mankind. I do not know a treatise in which a survey of the Universe—using the word in its most comprehensive and only legitimate acceptation—is taken at all:—and it may be as well here to mention that by the term “Universe,” wherever employed without qualification in this essay, I mean to designate the utmost conceivable expanse of space, with all things, spiritual and material, that can be imagined to exist within the compass of that expanse. In speaking of what is ordinarily implied by the expression, “Universe,” I shall take a phrase of limitation—“the 5 Universe of stars.”"; 
		String s;
		try {
			s = encode(test);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//decode(s);

	}

	private static HuffmanNode buildTree(Map<Character, Integer> freq) {

		PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
		Set<Character> keySet = freq.keySet();
		for (Character c : keySet) {

			HuffmanNode huffmanNode = new HuffmanNode();
			huffmanNode.data = c;
			huffmanNode.frequency = freq.get(c);
			huffmanNode.left = null;
			huffmanNode.right = null;
			priorityQueue.offer(huffmanNode);
		}
		assert priorityQueue.size() > 0;

		while (priorityQueue.size() > 1) {

			HuffmanNode x = priorityQueue.peek();
			priorityQueue.poll();

			HuffmanNode y = priorityQueue.peek();
			priorityQueue.poll();

			HuffmanNode sum = new HuffmanNode();

			sum.frequency = x.frequency + y.frequency;
			sum.data = '-';

			sum.left = x;

			sum.right = y;
			root = sum;

			priorityQueue.offer(sum);
		}

		return priorityQueue.poll();
	}


	private static void setPrefixCodes(HuffmanNode node, StringBuilder prefix) {

		if (node != null) {
			if (node.left == null && node.right == null) {
				charPrefixHashMap.put(node.data, prefix.toString());

			} else {
				prefix.append('0');
				setPrefixCodes(node.left, prefix);
				prefix.deleteCharAt(prefix.length() - 1);

				prefix.append('1');
				setPrefixCodes(node.right, prefix);
				prefix.deleteCharAt(prefix.length() - 1);
			}
		}

	}

	private static String encode(String test) throws IOException {
		Map<Character, Integer> freq = new HashMap<>();
		for (int i = 0; i < test.length(); i++) {
			if (!freq.containsKey(test.charAt(i))) {
				freq.put(test.charAt(i), 0);
			}
			freq.put(test.charAt(i), freq.get(test.charAt(i)) + 1);
		}

		//System.out.println("Character Frequency Map = " + freq);
		root = buildTree(freq);

		setPrefixCodes(root, new StringBuilder());
		//System.out.println("Character Prefix Map = " + charPrefixHashMap);
		StringBuilder s = new StringBuilder();

		for (int i = 0; i < test.length(); i++) {
			char c = test.charAt(i);
			//s.append(charPrefixHashMap.get(c));
			Files.write(Paths.get("D:\\MyDocuments\\Books\\output.txt"),charPrefixHashMap.get(c).getBytes(),StandardOpenOption.CREATE,StandardOpenOption.APPEND);
		}
		return s.toString();
	}

	private static void decode(String s) {

		StringBuilder stringBuilder = new StringBuilder();

		HuffmanNode temp = root;

		System.out.println("Encoded: " + s);

		for (int i = 0; i < s.length(); i++) {
			int j = Integer.parseInt(String.valueOf(s.charAt(i)));

			if (j == 0) {
				temp = temp.left;
				if (temp.left == null && temp.right == null) {
					stringBuilder.append(temp.data);
					temp = root;
				}
			}
			if (j == 1) {
				temp = temp.right;
				if (temp.left == null && temp.right == null) {
					stringBuilder.append(temp.data);
					temp = root;
				}
			}
		}

		//System.out.println("Decoded string is " + stringBuilder.toString());

	}
}

