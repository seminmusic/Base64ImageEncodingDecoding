package ba.sema;

import java.util.Arrays;
import java.util.List;

public class ResponseModel 
{
	private String securityQuestion;
	private List<ResponseSlika> listaSlika;
	private int[] correctAnswers;
	
	public String getSecurityQuestion() {
		return securityQuestion;
	}
	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}
	public List<ResponseSlika> getListaSlika() {
		return listaSlika;
	}
	public void setListaSlika(List<ResponseSlika> listaSlika) {
		this.listaSlika = listaSlika;
	}
	public int[] getCorrectAnswers() {
		return correctAnswers;
	}
	public void setCorrectAnswers(int[] correctAnswers) {
		this.correctAnswers = correctAnswers;
	}
	
	@Override 
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		//
		sb.append("\n----------------------------------------------------------------------------\n");
		sb.append("RESPONSE MODEL:\n");
		sb.append("---------------\n");
		sb.append("Security Question: " + securityQuestion + "\n");
		sb.append("Slike:\n");
		sb.append("[\n");
		for (ResponseSlika slika : listaSlika)
		{
			sb.append("    {\n");
			sb.append("        ID: " + slika.getId() + "\n");
			sb.append("        Base64Slika: " + slika.getBase64Slika().substring(0, 100) + "..." + "\n");
			sb.append("    }\n");
		}
		sb.append("]\n");
		sb.append("Correct Answers: " + Arrays.toString(correctAnswers) + "\n");
		sb.append("----------------------------------------------------------------------------");
		//
		return sb.toString();
	}
}
