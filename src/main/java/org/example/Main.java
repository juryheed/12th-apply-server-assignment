package org.example;

import java.util.*;
public class Main{
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);

        start start=new start();    //프로그램 시작과 관련된 객체
        Room Room=new Room();       //스터디 룸과 관련된 객체
        Inquiry Inquiry=new Inquiry();  //문의와 관련된 객체

        while(true) {
            start.Option();
            int N = sc.nextInt();       //N을 입력받는다
            System.out.println(" ");
            if (N == 1) {
                Room.Reserve();         //N=1이면 Room클래스의 Reverse함수 실행
            } else if (N == 2) {
                Room.Check();           //N=2 이면 Room클래스의 Check함수 실행
            } else if (N == 3) {
                Inquiry.Question();     //N=3 이면 Inquiry클래스의 Questin함수 실행
            } else if (N == 4) {
                Inquiry.Question_List();    //N=4 이면 Inquiry클래스의 Questin_List함수 실행
            }
            else if(N==5){
                System.out.println("프로그램을 종료합니다...");       //N=5 이면 프로그램 종료
                System.exit(0);
            }
            else{
                System.out.println("가능한 작업이 아닙니다. 1번~5번 사이에서 다시 선택해주세요");   //1~5사이의 숫자 외 다른 수를 누른 경우 안내문장 출력
            }
        }
    }
}

class start{
    void start() {      //생성자
        System.out.println("스터디룸 예약 프로그램입니다.");
    }

    //첫 화면에서 선택지를 고르는 함수
    void Option() {
        System.out.println("----- 작업 -----");
        System.out.println(" ");
        System.out.println("1. 스터디룸 예약");
        System.out.println("2. 예약 현황 조회");
        System.out.println("3.문의 남기기");
        System.out.println("4.문의 리스트 보기");
        System.out.println("5.프로그램 종료 ");
        System.out.println(" ");
        System.out.print("작업을 선택하세요: ");
    }
}


class Room {
    char[][] room=new char[24][3];    //행13, 열3인 이차원 문자배열을 생성한다.

    //생성자를 만든다.이래야 클래스가 호출되자마자 배열이 초기화 된다.
    Room(){
        room_reset();
    }
    //배열 초기화
    void room_reset() {
        for (int i = 10; i < 23; i++) {
            room[i][0] = 'X';   //위에서 만든 배열의 1열을 모두 X로 초기화 한다
            room[i][1] = 'X';   //위에서 만든 배열의 2열을 모두 X로 초기화 한다
            room[i][2] = 'X';   //위에서 만든 배열의 3열을 모두 X로 초기화 한다
        }
    }

    Scanner sc=new Scanner(System.in);


    //1.스터디룸 예약 함수
    void Reserve() {
        System.out.println("----- 스터디룸 예약 -----");

        System.out.print("예약할 스터디룸: ");
        char roomname = sc.next().charAt(0);  //문자(A또는 B또는 C) 입력받기
        int roomname_N; //베열 순서를 판단하기위해 입력받은 문자를 숫자로 변경한다(A->0,B->1, C->2 로 변경)
        if (roomname == 'A') {
            roomname_N = 0;
        } else if (roomname == 'B') {
            roomname_N = 1;
        } else
            roomname_N = 2;


        System.out.print("사용 시작 시간: ");     //예약 하고싶은 시작시간 입력
        int start = sc.nextInt();

        System.out.print("사용 종료 시간: ");     //예약하고싶은 종료시간 입력
        int end = sc.nextInt();

        outerLoop:
        //룸의 이름을 A,B,C중 하나로 정확하게 기입한 경우
        if(roomname=='A'||roomname=='B'||roomname=='C') {
            for (int i = start; i <= end; i++){     //예약을 희망하는 시간을 다 검사한다.
                for(int j=0;j<3;j++){
                    if (room[i][j] == 'O') {    //검사하던 배열의 원소가 O인 경우 이미 예약이 되어있다.
                        System.out.println("스터디룸" + roomname + "는 해당 시간에 이미 예약되어 있습니다.");
                        System.out.println("예약에 실패했습니다.");
                        System.out.println("");

                        break outerLoop;  //예약 확인 작업 종료
                    }
                    else {//선택한 시간중 모든 시간이 사용 가능 한 경우
                        room[i][roomname_N] = 'O';    //예약 정보를 예약된 상태인 0으로 변경
                    }
                }
            }
            System.out.println("예약이 완료되었습니다!");
            System.out.println(" ");
        }
        //존재하지 않는 방을 고른 경우(roomname 변수가 A,B,C중에 하나가 아닌것)
        else{
            System.out.println("스터디룸 " + roomname + "은 존재하지 않습니다.");
            System.out.println("예약에 실패했습니다.");
            System.out.println("");
        }
    }

    //2. 예약 현황 조회 함수
    void Check(){
        System.out.println("----- 예약 현황 -----");
        System.out.println("| A | B | C |");
        for(int i=10;i<13;i++){     //오전은 그대로 출력한다
            System.out.println("오전"+i+"시"+"| "+room[i][0]+" | "+room[i][1]+" | "+room[i][2]+" | " );
        }
        for(int i=13;i<23;i++){     //오후는 12시간을 빼서 출력한다. ex)13시->1시
            System.out.println("오후"+(i-12)+"시"+" | "+room[i][0]+" | "+room[i][1]+" | "+room[i][2]+" | " );
        }
    }
}


//문의 클래스
class Inquiry{

    Scanner sc=new Scanner(System.in); //Scanner 객체 생성
    ArrayList<String> ID=new ArrayList<String>();       //문의 ID 리스트 생성
    ArrayList<String> Question=new ArrayList<String>(); //문의 내용 리스트 생성

    //3. 문의 남기기 함수
    void Question(){
        System.out.println("----- 문의 남기기 -----");
        System.out.print("문의 아이디: ");
        String id= sc.nextLine();           //문의 아이디를 id변수에 입력받음
        System.out.print("문의 내용: ");
        String question= sc.nextLine();     //문의 내용을 question변수에 입력받음
        System.out.println("");

        ID.add(id);         //id리스트 맨 마지막에 id변수값을 추가한다
        Question.add(question);
        System.out.println("문의가 저장되었습니다!");     //question리스트 맨 마지막에 question변수값을 추가한다
        System.out.println("");
    }


    //4.문의 리스트 보기 함수
    void Question_List(){
        System.out.println("----- 문의 리스트 보기 -----");
        for(int i=0;i<ID.size();i++){       //생성된 리스트의 모든 값을 처음부터 끝까지 모두 출력한다
            System.out.println("["+(i+1)+"번 문의]");
            System.out.println("문의 아이디: "+ID.get(i));       //id리스트의 i번째 값을 출력한다
            System.out.println("문의 내용: "+Question.get(i));  //question리스트의 i번째 값을 출력한다
            System.out.println( );
        }
    }
}