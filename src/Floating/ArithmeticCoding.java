package Floating;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ArithmeticCoding {

	private Scanner in;
	ArrayList<Character> tablechar = new ArrayList<>();
	ArrayList<Float> tableprob = new ArrayList<>();
	ArrayList<Float> tablelow = new ArrayList<>();
	ArrayList<Float> tablehigh = new ArrayList<>();
	float Lower = 0, Lower1 = 0, Upper = 0, Rang = 0, CompressedCode = 0;
	String compressdata, decompressdata = "";

	public void Compress() throws IOException {

		String data = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader("ReadFile.txt"));
			String str = "";
			while ((str = in.readLine()) != null)
				data += str;
			in.close();
		} catch (IOException e) {
		}
		// System.out.println(data);
		int count = 0;
		char ch;
		while (count < data.length()) {
			ch = data.charAt(count);
			if (tablechar.contains(ch)) {
				count++;
			} else {
				tablechar.add(ch);
				count++;
			}
		}

		Collections.sort(tablechar);

		float len = data.length();
		int count1 = 0, count2 = 0;
		while (count2 < tablechar.size()) {
			char c = tablechar.get(count2);
			count1 = 0;
			for (int i = 0; i < data.length(); i++) {
				if (data.charAt(i) == c)
					count1++;
			}
			float num = count1 / len;
			tableprob.add(num);
			count2++;
		}

		for (int i = 0; i < tablechar.size(); i++) {
			System.out.println(tablechar.get(i) + "    tableprob    " + tableprob.get(i));
		}
		System.out.println();

		float low = 0, high = 0;
		for (int i = 0; i < tablechar.size(); i++) {
			if (i == 0) {
				low = i;
				high = tableprob.get(i);
				System.out.println("Low Rang  ( " + tablechar.get(i) + " ) = " + low);
				System.out.println("High Rang ( " + tablechar.get(i) + " ) = " + high);
				System.out.println();
				tablelow.add(low);
				tablehigh.add(high);
			} else {
				low = high;
				high = low + tableprob.get(i);
				System.out.println("Low Rang  ( " + tablechar.get(i) + " ) = " + low);
				System.out.println("High Rang ( " + tablechar.get(i) + " ) = " + high);
				System.out.println();
				tablelow.add(low);
				tablehigh.add(high);
			}
		}

		in = new Scanner(System.in);
		compressdata = in.next();
		for (int i = 0; i < compressdata.length(); i++) {
			char c = compressdata.charAt(i);
			System.out.println();
			System.out.println("For Symbol : " + c);
			if (i == 0) {
				for (int j = 0; j < tablechar.size(); j++) {
					if (c == (char) (j + 97)) {
						Lower = tablelow.get(j);
						Upper = tablehigh.get(j);
					}
					Rang = Upper - Lower;
				}
			} else {
				for (int j = 0; j < tablechar.size(); j++) {
					if (c == (char) (j + 97)) {
						Lower = Lower + (Rang * tablelow.get(j));
						Upper = Lower1 + (Rang * tablehigh.get(j));
					}
				}
				Rang = Upper - Lower;
			}
			Lower1 = Lower;
			System.out.println("    Lower  : " + Lower);
			System.out.println("    Upper  : " + Upper);
		}
		System.out.println();
		CompressedCode = (Lower + Upper) / 2;
		System.out.println("Comressed Code = " + CompressedCode);

		FileWriter writeCompressedCode = new FileWriter("Compressed Code.txt");
		BufferedWriter obj = new BufferedWriter(writeCompressedCode);
		obj.write((Float.toString(CompressedCode)));
		obj.close();
	}

	public void DeCompress() throws IOException {

		FileReader ReadCompressedCode = new FileReader("Compressed Code.txt");
		BufferedReader obj = new BufferedReader(ReadCompressedCode);
		String line = obj.readLine();
		obj.close();

		float CompressedCode1 = Float.parseFloat(line);
		float CompressedCode0 = Float.parseFloat(line);
		// System.out.println("asddssssssssf"+CompressedCode1);

		for (int i = 0; i < compressdata.length(); i++) {
			for (int a = 0; a < tablelow.size(); a++) {
				if (tablelow.get(a) < CompressedCode0 && CompressedCode0 < tablehigh.get(a)) {
					char c = tablechar.get(a);
					System.out.println();
					System.out.println("  Symbol is : " + c /* + " " + i */);
					decompressdata += c;
					if (i == 0) {
						Lower = tablelow.get(a);
						Upper = tablehigh.get(a);
						Rang = Upper - Lower;
					} else {
						// for (int j = 0; j < tablechar.size(); j++) {
						// if (c == (char) (j + 97)) {
						Lower = Lower + (Rang * tablelow.get(a));
						Upper = Lower1 + (Rang * tablehigh.get(a));
						// }
						// }
						Rang = Upper - Lower;
					}
					Lower1 = Lower;
					System.out.println("    Lower  : " + Lower);
					System.out.println("    Upper  : " + Upper);
					CompressedCode0 = (CompressedCode1 - Lower) / (Upper - Lower);
					break;
				}
			}
			System.out.println("Compressed Code : " + CompressedCode0);
		}
		System.out.println();
		System.out.println("DeCompressed Data : " + decompressdata);
	}

}
