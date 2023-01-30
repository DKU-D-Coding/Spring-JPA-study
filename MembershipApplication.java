package kr.ac.dankook;


import java.util.Scanner;
import java.util.ArrayList;



class Member {
	private String id;
	private String pw;
	private String name;
	
	
	public Member() {
		super();
	}


	public Member(String id, String pw, String name) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPw() {
		return pw;
	}


	public void setPw(String pw) {
		this.pw = pw;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
	
}

class MemberDAO {
	
	
	static ArrayList<Member> memlist = new ArrayList<Member> (10);
	//constructor with capacity
	
	
	//Method for Checking Id duplication
	boolean checkId(String id) {
		
		boolean check = false;
		for(int i = 0; i < memlist.size(); i++) {
			if(memlist.get(i).
					getId().equals(id)) {
				check = true;
				break;
			}
		}	
		return check;
	}
	
	
	
	
	
	boolean checkLogin(String id , String pw) {
		boolean check = false;
		for(int i = 0; i < memlist.size(); i++) {
			Member mb = memlist.get(i);
			
			if(mb.getId().equals(id) &&
			   mb.getPw().equals(pw)) {
				
				check = true;
				
				Controller.idx = i;
				break;
			}
			
		}	
		
			return check;		
			}








}

class Controller {
	Scanner sc = new Scanner(System.in);
	MemberDAO memberDAO = null;
	static int idx = -1;
	
	
	int printMenu() {
		String str1 = "=== [메인화면] ===";
		String str2 = "[1]가입 [2]로그인 [0]종료";
		
		System.out.println(str1);
		System.out.println(str2);
		
		
		int menu = sc.nextInt();
		
		return menu;
	}
	
	int printUserMenu(String name) {
		
		String str1 = "[" + name + "님 로그인중]";
		String str2 = "[1] 로그아웃 [2] 비밀번호변경 [3] 정보확인 [4] 회원탈퇴";
		
		System.out.println(str1);
		System.out.println(str2);
		
		return sc.nextInt();
		
	}
	
	String idInput() {
		System.out.println("아이디를 입력하시오: ");
		
		
		return sc.nextLine();
		
		
	}
	
	String pwInput() {
		System.out.println("비밀번호를 입력하시오: ");
		
		
		return sc.nextLine();
			
	}
	
	String nameInput() {
		System.out.println("아이디를 입력하시오: ");
		
		
		return sc.nextLine();
	}
	
	
	Member join(String id)	{
		
		String pw = pwInput();
			
		String name = nameInput();
			
			
			return new Member(id, pw, name);		
		
	}
	
	
	
	void init(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	
	
	
	void run() {		
		while(true) {
			
			
			int sel = printMenu();
			
			
			
			if(sel == 1) {
				sc.nextLine();
				
				String id = idInput();
				
				
				if(memberDAO.checkId(id)) {
					System.out.println("존재하는 아이디입니다.");
				
				
				} else memberDAO.memlist.add(join(id));
					
				continue;
				
			}else if(sel == 2) {
				sc.nextLine();
				
				String id = idInput();
				
				String pw = pwInput();
				
				
				if(memberDAO.checkLogin(id, pw)) {
					
					System.out.println("로그인 성공");					
					
					while(true) { 
						
						int sel2 = printUserMenu(MemberDAO.memlist.
								get(idx).getName());
						
						
						
						if(sel2 == 1) {
						System.out.println("로그아웃합니다.");
						idx = -1;
						break;
						
						
					} else if(sel2 == 2) {
						sc.nextLine();
						
						String newPw = pwInput();
						memberDAO.memlist.get(idx).
							setPw(newPw);
						
						continue;
						
						
						
						
					} else if(sel2 == 3) {
						
						System.out.printf("%s(%s, %s)\n", memberDAO.memlist.get(idx).getName(),
								memberDAO.memlist.get(idx).getId(),
								memberDAO.memlist.get(idx).getPw());
						
						continue;
						
						
						
					} else if(sel2 == 4) {
							memberDAO.memlist.remove(idx);
							idx = -1;
							System.out.println("회원탈퇴 완료");
						
							break;
						}
					
					}
					
					
					
				}else {
					System.out.println("로그인실패");
					
					continue;
				}
				
				
				
			}else if(sel == 0) {
				break;
			}
		}
	}
}

public class MembershipApplication {
	public static void main(String[] args) {
		
		Controller controller = new Controller();
		MemberDAO memberDAO = new MemberDAO();
		
		controller.init(memberDAO);
		controller.run();
		
	}
}
