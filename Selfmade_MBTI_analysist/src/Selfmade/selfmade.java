package Selfmade;
import java.util.*;

public class selfmade {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Map<String, Integer> type_scores = new HashMap<>();
		
		System.out.println("이 프로그램은 에니어그램을 통한 MBTI를 추정하는 프로그램입니다.");
		System.out.println("각 단계별 가중치와 통계 MBTI를 입력하시면 됩니다.");
		
		while(true) {
			System.out.println("\n== 새로운 입력 ==");
			System.out.print("가중치를 입력하세요: ");
			int weight = Integer.parseInt(input.nextLine());
			System.out.print("해당 가중치를 적용할 MBTI들을 공백으로 구분하여 입력하세요: ");
			String mbtis = input.nextLine().replace(" ", "");
			System.out.println(mbtis);
			
			System.out.println(String.format("가중치: %d", weight));
			for(int i=0;i<mbtis.length();i++) {
				String curr_letter = String.valueOf(mbtis.charAt(i));
				type_scores.compute(curr_letter, (key, value) -> (value == null) ? weight : value + weight);
			}
			
			System.out.println(String.format("현재 스코어 상태: { %s }", type_scores));
			
			String char_to_end = "M";
			System.out.print(String.format("추가 입력을 원하시면 문자'%s'을 입력해주세요: ", char_to_end));
			boolean want_more = input.nextLine().equals(char_to_end);
			
			if(!want_more) break;
		}
		
		System.out.println(String.format("최종 스코어: { %s }", type_scores));
		
		input.close();
	}
}
