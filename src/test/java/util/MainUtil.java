package util;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by André Buzzo on 05/12/17.
 */
public class MainUtil {

	/**
	 * ISO8859 = "ISO-8859-1"
	 */
	public static final String ISO88591 = "ISO-8859-1";

	public static List<String> obterCenariosTestTable(Class<?> pClasseTeste) throws IOException {
		final List<String> cenarios = obterLinhasDoArquivo(pClasseTeste, "testTable.txt");
		cenarios.remove(0); // linha 0 corresponde ao titulo das colunas
		return cenarios;
	}

	public static List<String> obterLinhasDoArquivo(Class<?> pClasseTeste, String pNomeArquivo) throws IOException {
		final File testTable = new File(
				"src/test/java/" + pClasseTeste.getPackage().getName().replaceAll("\\.", "/") + "/" + pNomeArquivo);
		final FileInputStream inputStream = new FileInputStream(testTable);
		final List<String> cenarios = IOUtils.readLines(inputStream, MainUtil.ISO88591);
		final Iterator<String> iterator = cenarios.iterator();
		while (iterator.hasNext()) {
			final String cenario = iterator.next();
			if (cenario.isEmpty() || cenario.startsWith("!--") || cenario.startsWith("#")) {
				iterator.remove();
			}
		}
		return cenarios;
	}
}