/*On my honor, I have neither given nor received unauthorized aid on this assignment 
 * Author Fang Bin 
 * Wed Apr 8 2014
 * */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class MIPSsim {

	/**
	 * @param args
	 */
	public static  Map<Integer,List> line_code = new HashMap<Integer,List>(); 
	public static  Map<Integer,List> data = new HashMap<Integer,List>(); //<line,<add,value>>
	public static  List<String> codelist=new ArrayList<String>();          //the input file           
	public static  List<Integer> registers=new ArrayList<Integer>();          //the input file           
	public static  Map<String,Integer> register_table = new HashMap<String,Integer>(); 
	public static int pc;
	public static int Cycle=1;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		readfile();
		init();
		simulator();
	}
	public static void readfile() throws IOException{
		Scanner sc = new Scanner(System.in);
		System.out.println("please input fine name:");
		String fileName = sc.nextLine();
		
		//BufferedReader br = new BufferedReader(new FileReader("sample.txt"));
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		System.out.println("reading file...");
		String str=null;
		try {
			while((str=br.readLine())!=null){
				codelist.add(str);
			 }
		} catch (IOException e) {
			e.printStackTrace();
		} 
		System.out.println("reading file end");
		 for(int i=0;i<codelist.size();i++){
			 //System.out.println(codelist.get(i));
			 //System.out.println("decoding instructions...");			 
			 decode(i,codelist.get(i));
		 }
	}
	public static void decode(int i,String onecode) throws IOException{
		List<String> temp=new ArrayList<String>(); 
		String opcode=onecode.substring(0, 6);		 
		
		File info=new File("out-fb.txt"); 
		if(!info.exists())        info.createNewFile();
        FileOutputStream file=new FileOutputStream(info,true);
        PrintStream p=new PrintStream(file); 
		
        switch (opcode){
		//--------------------------------------J-------------------------------------
		//category-01
		//instr_index 26
		//J instr_index
		case "010000": 
			//instr_index 6-32
			int add1=256+i*4;
			String instr_index=onecode.substring(6);
			int J_index= Integer.parseInt(instr_index, 2)*4;
			
			temp.add(String.valueOf(add1));				//add address   1
			temp.add("J");								//add instruction type 2
			temp.add(String.valueOf(J_index));			//add J_instr_index  3
			line_code.put(i, temp);
			 
			System.out.println(onecode+"\t"+add1+"\tJ  #"+J_index);
			p.println(onecode+"\t"+add1+"\tJ #"+J_index);
			 
			break;
			
		case "010001": 
			//--------------------------------------JR-------------------------------------
			//rs 6-11
			//JR rs
			int add_jr=256+i*4;
			String JR_rs=onecode.substring(6,11);			
			int jr_rs= Integer.parseInt(JR_rs, 2);
			
			temp.add(String.valueOf(add_jr));
			temp.add("JR");//
			temp.add(String.valueOf(jr_rs));
			line_code.put(i, temp);
			
			System.out.println(onecode+"\t"+add_jr+"\t"+"JR "+"#"+jr_rs);
			p.println(onecode+"\t"+add_jr+"\t"+"JR "+"#"+jr_rs);
		 
			break;
			
		case "010010": 
			//--------------------------------------BEQ-------------------------------------
			//BEQ rs, rt, offset
			int add_beq=256+i*4;
					
			String BEQ_rs=onecode.substring(6,11);
			String BEQ_rt=onecode.substring(11,16);
			String BEQ_offset=onecode.substring(16);
			
			int beq_rs= Integer.parseInt(BEQ_rs, 2);
			int beq_rt= Integer.parseInt(BEQ_rt, 2);
			int offset= Integer.parseInt(BEQ_offset, 2)*4;
			
			temp.add(String.valueOf(add_beq));
			temp.add("BEQ");
			temp.add(String.valueOf(beq_rs));
			temp.add(String.valueOf(beq_rt));
			temp.add(String.valueOf(offset));
			line_code.put(i, temp);
			System.out.println(onecode+"\t"+add_beq+"\t"+"BEQ "+"R"+beq_rs+", R"+beq_rt+", #"+offset);
			p.println(onecode+"\t"+add_beq+"\t"+"BEQ "+"R"+beq_rs+", R"+beq_rt+", #"+offset);
			
			break;
		
		case "010011":
			//--------------------------------------BLTZ-------------------------------------
			//rs 00000 offset
			int add_bltz=256+i*4;
			String BLTZ_rs=onecode.substring(6,11);
			String BLTZ_offset=onecode.substring(16);
			
			int bltz_rs= Integer.parseInt(BLTZ_rs, 2);
			int bltz_offset= Integer.parseInt(BLTZ_offset, 2)*4;
			
			temp.add(String.valueOf(add_bltz));
			temp.add("BLTZ");
			temp.add(String.valueOf(bltz_rs));
			temp.add(String.valueOf(bltz_offset));
			line_code.put(i, temp);
			 
			System.out.println(onecode+"\t"+add_bltz+"\t"+"BLTZ "+"R"+bltz_rs+", #"+bltz_offset);
			p.println(onecode+"\t"+add_bltz+"\t"+"BLTZ "+"R"+bltz_rs+", #"+bltz_offset);
			
			break;
		
		case "010100": 
			//--------------------------------------BGTZ-------------------------------------
			//rs 00000 offset
			int add_bgtz=256+i*4;
			String BGTZ_rs=onecode.substring(6,11);
			String BGTZ_offset=onecode.substring(16);
			
			int bgtz_rs= Integer.parseInt(BGTZ_rs, 2);
			int bgtz_offset= Integer.parseInt(BGTZ_offset, 2)*4;
			
			temp.add(String.valueOf(add_bgtz));
			temp.add("BGTZ");
			temp.add(String.valueOf(bgtz_rs));
			temp.add(String.valueOf(bgtz_offset));
			line_code.put(i, temp);
			 
			System.out.println(onecode+"\t"+add_bgtz+"\t"+"BGTZ "+"R"+bgtz_rs+", #"+bgtz_offset);
			p.println(onecode+"\t"+add_bgtz+"\t"+"BGTZ "+"R"+bgtz_rs+", #"+bgtz_offset);
			break;
		case "010101": 
			//--------------------------------------BREAK-------------------------------------
			
			int add_break=256+i*4;
			temp.add(String.valueOf(add_break));
			temp.add("BREAK");
			line_code.put(i, temp);
			System.out.println(onecode+"\t"+add_break+"\tBREAK");
			p.println(onecode+"\t"+add_break+"\tBREAK");
			break;
		
		case "010110": 
			//--------------------------------------SW-------------------------------------
			//base5  rt5  offset16
			//SW rt, offset(base)
			int add_sw=256+i*4;
			String SW_base=onecode.substring(6,11);
			String SW_rt=onecode.substring(11,16);
			String SW_offset=onecode.substring(16);
			
			int sw_base= Integer.parseInt(SW_base, 2);
			int sw_rt= Integer.parseInt(SW_rt, 2);
			int sw_offset= Integer.parseInt(SW_offset, 2);
			
			temp.add(String.valueOf(add_sw));
			temp.add("SW");
			temp.add(String.valueOf(sw_base));
			temp.add(String.valueOf(sw_rt));
			temp.add(String.valueOf(sw_offset));
			line_code.put(i, temp);
			
 
			System.out.println(onecode+"\t"+add_sw+"\t"+"SW "+"R"+sw_rt+", "+sw_offset+"(R"+sw_base+")");
			p.println(onecode+"\t"+add_sw+"\t"+"SW "+"R"+sw_rt+", "+sw_offset+"(R"+sw_base+")");
			break;
		
		
		case "010111": 
			//--------------------------------------LW-------------------------------------
			//base5  rt5  offset16
			//LW rt, offset(base)
			int add_lw=256+i*4;
			String LW_base=onecode.substring(6,11);
			String LW_rt=onecode.substring(11,16);
			String LW_offset=onecode.substring(16);
			
			int lw_base= Integer.parseInt(LW_base, 2);
			int lw_rt= Integer.parseInt(LW_rt, 2);
			int lw_offset= Integer.parseInt(LW_offset, 2);
			
			temp.add(String.valueOf(add_lw));
			temp.add("LW");
			temp.add(String.valueOf(lw_base));
			temp.add(String.valueOf(lw_rt));
			temp.add(String.valueOf(lw_offset));
			line_code.put(i, temp);
			
			System.out.println(onecode+"\t"+add_lw+"\t"+"LW "+"R"+lw_rt+", "+lw_offset+"(R"+lw_base+")");
			p.println(onecode+"\t"+add_lw+"\t"+"LW "+"R"+lw_rt+", "+lw_offset+"(R"+lw_base+")");
			break;
		
		
		case "011000": 
			//--------------------------------------SLL-------------------------------------
			//00000 rt rd sa 000000
			//SLL rd, rt, sa
			
			int add_sll=256+i*4;
			String SLL_rt=onecode.substring(11,16);
			String SLL_rd=onecode.substring(16,21);
			String SLL_sa=onecode.substring(21,26);
			
			int sll_rt= Integer.parseInt(SLL_rt, 2);
			int sll_rd= Integer.parseInt(SLL_rd, 2);
			int sll_sa= Integer.parseInt(SLL_sa, 2);
			
			temp.add(String.valueOf(add_sll));
			temp.add("SLL");
			temp.add(String.valueOf(sll_rd));
			temp.add(String.valueOf(sll_rt));
			temp.add(String.valueOf(sll_sa));
			line_code.put(i, temp);
			
			System.out.println(onecode+"\t"+add_sll+"\t"+"SLL "+"R"+sll_rd+", R"+sll_rt+", #"+sll_sa);
			p.println(onecode+"\t"+add_sll+"\t"+"SLL "+"R"+sll_rd+", R"+sll_rt+", #"+sll_sa);
			break;
			
		case "011001": 
			//--------------------------------------SRL-------------------------------------
			//00000 rt rd sa XXXXXX
			//SRL rd, rt, sa
						
			int add_srl=256+i*4;
			String SRL_rt=onecode.substring(11,16);
			String SRL_rd=onecode.substring(16,21);
			String SRL_sa=onecode.substring(21,26);
			
			int srl_rt= Integer.parseInt(SRL_rt, 2);
			int srl_rd= Integer.parseInt(SRL_rd, 2);
			int srl_sa= Integer.parseInt(SRL_sa, 2);
			
			temp.add(String.valueOf(add_srl));
			temp.add("SRL");
			temp.add(String.valueOf(srl_rd));
			temp.add(String.valueOf(srl_rt));
			temp.add(String.valueOf(srl_sa));
			line_code.put(i, temp);
			
			System.out.println(onecode+"\t"+add_srl+"\t"+"SRL "+"R"+srl_rd+", R"+srl_rt+", #"+srl_sa);
			p.println(onecode+"\t"+add_srl+"\t"+"SRL "+"R"+srl_rd+", R"+srl_rt+", #"+srl_sa);
			break;
			
						
		case "011010": 
			//--------------------------------------SRA-------------------------------------
			//00000 rt rd sa XXXXXX
			//SRA rd, rt, sa
			
			int add_sra=256+i*4;
			String SRA_rt=onecode.substring(11,16);
			String SRA_rd=onecode.substring(16,21);
			String SRA_sa=onecode.substring(21,26);
			
			int sra_rt= Integer.parseInt(SRA_rt, 2);
			int sra_rd= Integer.parseInt(SRA_rd, 2);
			int sra_sa= Integer.parseInt(SRA_sa, 2);
			
			temp.add(String.valueOf(add_sra));
			temp.add("SRA");
			temp.add(String.valueOf(sra_rd));
			temp.add(String.valueOf(sra_rt));
			temp.add(String.valueOf(sra_sa));
			line_code.put(i, temp);
			
			System.out.println(onecode+"\t"+add_sra+"\t"+"SRA "+"R"+sra_rd+", R"+sra_rt+", #"+sra_sa);
			p.println(onecode+"\t"+add_sra+"\t"+"SRA "+"R"+sra_rd+", R"+sra_rt+", #"+sra_sa);
			break;
			
			
			
			
		case "011011": 
			//--------------------------------------NOP-------------------------------------
			//00000 00000 00000 00000 XXXXXX
			int add_nop=256+i*4;
			
			temp.add(String.valueOf(add_nop));			
			temp.add("NOP");
			line_code.put(i, temp);
			System.out.println(onecode+"\t"+add_nop+"\t"+"NOP");
			p.println(onecode+"\t"+add_nop+"\t"+"NOP");
			
			break;
		
	 	//category-02
		case "110000": 
			//--------------------------------------ADD-------------------------------------
			//rs rt rd 00000 
			//ADD rd,rs,rt
			int add_add=256+i*4;
						
			//System.out.println("ADD\n");
			String ADD_rs=onecode.substring(6,11);
			String ADD_rt=onecode.substring(11,16);
			String ADD_rd=onecode.substring(16,21);
			
			int rs= Integer.parseInt(ADD_rs, 2);
			int rt= Integer.parseInt(ADD_rt, 2);
			int rd= Integer.parseInt(ADD_rd, 2);
			
			temp.add(String.valueOf(add_add));
			temp.add("ADD");
			temp.add(String.valueOf(rd));
			temp.add(String.valueOf(rs));
			temp.add(String.valueOf(rt));
			line_code.put(i, temp);
			
			System.out.println(onecode+"\t"+add_add+"\tADD "+"R"+rd+", R"+rs+", R"+rt);
			p.println(onecode+"\t"+add_add+"\tADD "+"R"+rd+", R"+rs+", R"+rt);
			
			break;
		
		 
		case "110001": 
			//--------------------------------------SUB-------------------------------------
			//rs rt rd 00000 
			//SUB rd, rs, rt
			int add_sub=256+i*4;
			
			String SUB_rs=onecode.substring(6,11);
			String SUB_rt=onecode.substring(11,16);
			String SUB_rd=onecode.substring(16,21);
			
			int srs= Integer.parseInt(SUB_rs, 2);
			int srt= Integer.parseInt(SUB_rt, 2);
			int srd= Integer.parseInt(SUB_rd, 2);
			
			temp.add(String.valueOf(add_sub));
			temp.add("SUB");
			temp.add(String.valueOf(srd));
			temp.add(String.valueOf(srs));
			temp.add(String.valueOf(srt));
			
			System.out.println(onecode+"\t"+add_sub+"\tSUB "+"R"+srd+", R"+srs+", R"+srt);
			p.println(onecode+"\t"+add_sub+"\tSUB "+"R"+srd+", R"+srs+", R"+srt);
			line_code.put(i, temp);
			break;
			
			
			
		case "110010": 
			//--------------------------------------MUL-------------------------------------
			//rs rt rd 00000 
			//MUL rd, rs, rt
			int add_mul=256+i*4;
			
			String MUL_rs=onecode.substring(6,11);
			String MUL_rt=onecode.substring(11,16);
			String MUL_rd=onecode.substring(16,21);
			
			int mrs= Integer.parseInt(MUL_rs, 2);
			int mrt= Integer.parseInt(MUL_rt, 2);
			int mrd= Integer.parseInt(MUL_rd, 2);
			
			temp.add(String.valueOf(add_mul));
			temp.add("MUL");
			temp.add(String.valueOf(mrd));
			temp.add(String.valueOf(mrs));
			temp.add(String.valueOf(mrt));
			
			System.out.println(onecode+"\t"+add_mul+"\tMUL "+"R"+mrd+", R"+mrs+", R"+mrt);
			p.println(onecode+"\t"+add_mul+"\tMUL "+"R"+mrd+", R"+mrs+", R"+mrt);
			line_code.put(i, temp);
			break;
			
			
			
			
			
		case "110011": 
			//--------------------------------------AND-------------------------------------
			//rs rt rd 00000 
			//AND rd, rs, rt
			int add_and=256+i*4;
			
			String AND_rs=onecode.substring(6,11);
			String AND_rt=onecode.substring(11,16);
			String AND_rd=onecode.substring(16,21);
			
			int ars= Integer.parseInt(AND_rs, 2);
			int art= Integer.parseInt(AND_rt, 2);
			int ard= Integer.parseInt(AND_rd, 2);
			
			temp.add(String.valueOf(add_and));
			temp.add("AND");
			temp.add(String.valueOf(ard));
			temp.add(String.valueOf(ars));
			temp.add(String.valueOf(art));
			
			System.out.println(onecode+"\t"+add_and+"\tAND "+"R"+ard+", R"+ars+", R"+art);
			p.println(onecode+"\t"+add_and+"\tAND "+"R"+ard+", R"+ars+", R"+art);
			line_code.put(i, temp);
			break;
		 
		case "110100": 
			
			//--------------------------------------OR-------------------------------------
			//rs rt rd 00000 
			//OR rd, rs, rt
			int add_or=256+i*4;
			
			String OR_rs=onecode.substring(6,11);
			String OR_rt=onecode.substring(11,16);
			String OR_rd=onecode.substring(16,21);
			
			int ors= Integer.parseInt(OR_rs, 2);
			int ort= Integer.parseInt(OR_rt, 2);
			int ord= Integer.parseInt(OR_rd, 2);
			
			temp.add(String.valueOf(add_or));
			temp.add("OR");
			temp.add(String.valueOf(ord));
			temp.add(String.valueOf(ors));
			temp.add(String.valueOf(ort));
			
			System.out.println(onecode+"\t"+add_or+"\tOR "+"R"+ord+", R"+ors+", R"+ort);
			p.println(onecode+"\t"+add_or+"\tOR "+"R"+ord+", R"+ors+", R"+ort);
			line_code.put(i, temp);
			break;
			
			 
			
		case "110101": 
			//--------------------------------------XOR-------------------------------------
			//rs rt rd 00000 
			//XOR rd, rs, rt
			int add_xor=256+i*4;
			
			String XOR_rs=onecode.substring(6,11);
			String XOR_rt=onecode.substring(11,16);
			String XOR_rd=onecode.substring(16,21);
			
			int xrs= Integer.parseInt(XOR_rs, 2);
			int xrt= Integer.parseInt(XOR_rt, 2);
			int xrd= Integer.parseInt(XOR_rd, 2);
			
			temp.add(String.valueOf(add_xor));
			temp.add("XOR");
			temp.add(String.valueOf(xrd));
			temp.add(String.valueOf(xrs));
			temp.add(String.valueOf(xrt));
			
			System.out.println(onecode+"\t"+add_xor+"\tXOR "+"R"+xrd+", R"+xrs+", R"+xrt);
			p.println(onecode+"\t"+add_xor+"\tXOR "+"R"+xrd+", R"+xrs+", R"+xrt);
			line_code.put(i, temp);
			break;
			
		case "110110": 
			//--------------------------------------NOR-------------------------------------
			//rs rt rd 00000 
			//NOR rd, rs, rt
			int add_nor=256+i*4;
			
			String NOR_rs=onecode.substring(6,11);
			String NOR_rt=onecode.substring(11,16);
			String NOR_rd=onecode.substring(16,21);
			
			int nrs= Integer.parseInt(NOR_rs, 2);
			int nrt= Integer.parseInt(NOR_rt, 2);
			int nrd= Integer.parseInt(NOR_rd, 2);
			
			temp.add(String.valueOf(add_nor));
			temp.add("NOR");
			temp.add(String.valueOf(nrd));
			temp.add(String.valueOf(nrs));
			temp.add(String.valueOf(nrt));
			
			System.out.println(onecode+"\t"+add_nor+"\tNOR "+"R"+nrd+", R"+nrs+", R"+nrt);
			p.println(onecode+"\t"+add_nor+"\tNOR "+"R"+nrd+", R"+nrs+", R"+nrt);
			line_code.put(i, temp);
			break;
			 
		case "110111": 
			
			//--------------------------------------SLT-------------------------------------
			//rs rt rd 00000 
			//SLT rd, rs, rt
			int add_slt=256+i*4;
			
			String SLT_rs=onecode.substring(6,11);
			String SLT_rt=onecode.substring(11,16);
			String SLT_rd=onecode.substring(16,21);
			
			int slrs= Integer.parseInt(SLT_rs, 2);
			int slrt= Integer.parseInt(SLT_rt, 2);
			int slrd= Integer.parseInt(SLT_rd, 2);
			
			temp.add(String.valueOf(add_slt));
			temp.add("SLT");
			temp.add(String.valueOf(slrd));
			temp.add(String.valueOf(slrs));
			temp.add(String.valueOf(slrt));
			
			System.out.println(onecode+"\t"+add_slt+"\tSLT "+"R"+slrd+", R"+slrs+", R"+slrt);
			p.println(onecode+"\t"+add_slt+"\tSLT "+"R"+slrd+", R"+slrs+", R"+slrt);
			line_code.put(i, temp);
			break;
			 
			
		case "111000": 
			//--------------------------------------ADDI-------------------------------------
			//rs rt
			//ADDI rt rs immediate
			int add_addi=256+i*4;
			
			
			//System.out.println("ADD\n");
			String ADDI_rs=onecode.substring(6,11);
			String ADDI_rt=onecode.substring(11,16);
			String ADDI_immediate=onecode.substring(16);
			
			int addi_rs= Integer.parseInt(ADDI_rs, 2);
			int addi_rt= Integer.parseInt(ADDI_rt, 2);
			int immediate= Integer.parseInt(ADDI_immediate, 2);
			
			temp.add(String.valueOf(add_addi));
			temp.add("ADDI");
			temp.add(String.valueOf(addi_rt));
			temp.add(String.valueOf(addi_rs));
			temp.add(String.valueOf(immediate));
			line_code.put(i, temp);
			System.out.println(onecode+"\t"+add_addi+"\tADDI "+"R"+addi_rt+", R"+addi_rs+", #"+immediate);
			p.println(onecode+"\t"+add_addi+"\tADDI "+"R"+addi_rt+", R"+addi_rs+", #"+immediate);
			break;
		
		case "111001": 
			//--------------------------------------ANDI-------------------------------------			
			//rs5  rt5   immediate16
			//ADDI rt rs immediate	
			int add_andi=256+i*4;
			 
			String ANDI_rs=onecode.substring(6,11);
			String ANDI_rt=onecode.substring(11,16);
			String ANDI_immediate=onecode.substring(16);
			
			int andi_rs= Integer.parseInt(ANDI_rs, 2);
			int andi_rt= Integer.parseInt(ANDI_rt, 2);
			int andi_immediate= Integer.parseInt(ANDI_immediate, 2);
			
			temp.add(String.valueOf(add_andi));
			temp.add("ANDI");
			temp.add(String.valueOf(andi_rt));
			temp.add(String.valueOf(andi_rs));
			temp.add(String.valueOf(andi_immediate));
			line_code.put(i, temp);
			System.out.println(onecode+"\t"+add_andi+"\tANDI "+"R"+andi_rt+", R"+andi_rs+", #"+andi_immediate);
			p.println(onecode+"\t"+add_andi+"\tANDI "+"R"+andi_rt+", R"+andi_rs+", #"+andi_immediate);
			
			break;
			
			 
			
		case "111010": 
			//--------------------------------------ORI-------------------------------------			
			//rs5  rt5   immediate16
			//ORI rt, rs, immediate
			int add_ori=256+i*4;
			 
			String ORI_rs=onecode.substring(6,11);
			String ORI_rt=onecode.substring(11,16);
			String ORI_immediate=onecode.substring(16);
			
			int ori_rs= Integer.parseInt(ORI_rs, 2);
			int ori_rt= Integer.parseInt(ORI_rt, 2);
			int ori_immediate= Integer.parseInt(ORI_immediate, 2);
			
			temp.add(String.valueOf(add_ori));
			temp.add("ORI");
			temp.add(String.valueOf(ori_rt));
			temp.add(String.valueOf(ori_rs));
			temp.add(String.valueOf(ori_immediate));
			line_code.put(i, temp);
			System.out.println(onecode+"\t"+add_ori+"\tORI "+"R"+ori_rt+", R"+ori_rs+", #"+ori_immediate);
			p.println(onecode+"\t"+add_ori+"\tORI "+"R"+ori_rt+", R"+ori_rs+", #"+ori_immediate);
			
			break;
			 
		case "111011": 
			//--------------------------------------XORI-------------------------------------			
			//rs5  rt5   immediate16
			//XORI rt, rs, immediate
			int add_xori=256+i*4;
			 
			String XORI_rs=onecode.substring(6,11);
			String XORI_rt=onecode.substring(11,16);
			String XORI_immediate=onecode.substring(16);
			
			int xori_rs= Integer.parseInt(XORI_rs, 2);
			int xori_rt= Integer.parseInt(XORI_rt, 2);
			int xori_immediate= Integer.parseInt(XORI_immediate, 2);
			
			temp.add(String.valueOf(add_xori));
			temp.add("XORI");
			temp.add(String.valueOf(xori_rt));
			temp.add(String.valueOf(xori_rs));
			temp.add(String.valueOf(xori_immediate));
			line_code.put(i, temp);
			System.out.println(onecode+"\t"+add_xori+"\tXORI "+"R"+xori_rt+", R"+xori_rs+", #"+xori_immediate);
			p.println(onecode+"\t"+add_xori+"\tXORI "+"R"+xori_rt+", R"+xori_rs+", #"+xori_immediate);
			
			break;
			
			default:
				//--------------------------------------DATA-------------------------------------			
				
				int add_im=256+i*4;
				String tr="";
				int num;
				
				if(onecode.substring(0, 1).equals("1")){//is not a positive number,reverse it and plus 1
					for(int k=0;k<onecode.length();k++){
						if(onecode.substring(k, k+1).equals("1"))
							 tr=tr+"0";
						else 
							tr=tr+"1";	
					} 
					 
					num= -(Integer.parseInt(tr, 2)+1);
				}
				else  
					num= Integer.parseInt(onecode, 2);
				 
				temp.add(String.valueOf(add_im));//data:address,value
				//temp.add("data");
				temp.add(String.valueOf(num));
				
				data.put(i, temp);			//linenum, <address,value>
				//line_code.put(i, temp);
				System.out.println(onecode+"\t"+add_im+"\t"+num);
				p.println(onecode+"\t"+add_im+"\t"+num);			 
		}
        p.close();
         
	}
	public static void init(){
		System.out.println("initialising registers...");
		for(int j=0;j<32;j++){
			String key="R"+String.valueOf(j);
			register_table.put(key, 0);
			registers.add(0);
		}
		/*for(int t=0;t<registers.size();t++){
			
			System.out.print(registers.get(t)+"\t");
			if ((t+1)%8==0)  System.out.println();
		}*/
	}
	public static void print_register() throws IOException{
		
		File info=new File("dis-fb.txt"); 
		if(!info.exists())       
			info.createNewFile();
        FileOutputStream file=new FileOutputStream(info,true);
        PrintStream p=new PrintStream(file); 
        
        p.println("\nRegisters");
		System.out.println("\nRegisters");
		for(int t=0;t<registers.size();t++){		
			if((t%8)==0) {
				if(t>=10) {
					System.out.print("R"+t+":\t");
					p.print("R"+t+":\t");}
			    if(t<10){
			    	System.out.print("R0"+t+":\t");
			    	p.print("R0"+t+":\t");
			    }
			}
			
			System.out.print(registers.get(t)+"\t");
			p.print(registers.get(t)+"\t");
			if ((t+1)%8==0)  {
				System.out.println();
				p.println();
			}
		}
		System.out.println();
		p.println();
		p.close();
	}
	public static void print_data() throws IOException{
		File info=new File("dis-fb.txt"); 
		if(!info.exists())       
			info.createNewFile();
        FileOutputStream file=new FileOutputStream(info,true);
        PrintStream p=new PrintStream(file); 
        
		int fadd=256+line_code.size()*4;
		int fline=line_code.size();
		System.out.println("Data");
		p.println("Data");
		
		for(int t=0;t<data.size();t++){
			if((t%8)==0) {
					System.out.print(fadd+(t*4)+":\t");
					p.print(fadd+(t*4)+":\t");
			}
			System.out.print(data.get(fline+t).get(1)+"\t");
			p.print(data.get(fline+t).get(1)+"\t");
			if ((t+1)%8==0)  {
				System.out.println();
				p.println();
			}	
		}
		System.out.println();
		p.println();
	}
	public static void print_inst(List inst_list){
		String inst_type=(String) inst_list.get(1);
		switch (inst_type){
		case "J":
			break;
		case "JR":break;
		case "BEQ":break;
		case "BLTZ":break;
		case "BGTZ":break;
		case "BREAK":break;
		case "SW":break;
		case "LW":break;
		case "SLL":break;
		case "SRL":break;
		case "SRA":break;
		case "NOP":break;
		case "ADD":break;
		case "SUB":break;
		case "MUL":break;
		case "AND":break;
		case "OR":break;
		case "XOR":break;
		case "NOR":break;
		case "SLT":break;
		case "ADDI":break;
		case "ANDI":break;
		case "ORI":break;
		case "XORI":break;
		}
		 //System.out.println(line_code);
	}
	
	public static void simulator() throws IOException{		
		String address=(String) line_code.get(0).get(0);
		int add=Integer.valueOf(address);
		pc=add;
		run_add(pc);
	}
	public static void run_add(int add) throws IOException{
		/*1.find which instruction has the address exactly equal to add
		 *2.run this instruction
		*/
		String type_inst;		
		for(int i=0;i<line_code.size();i++){
			String address=(String) line_code.get(i).get(0);
			int temp=Integer.valueOf(address);
			if(temp==add){
				type_inst=(String) line_code.get(i).get(1);
				run_inst(type_inst,line_code.get(i));		//run this instruction
				break;										//jump out for loop 
			}
		}
	}
		
	public static void run_inst(String type,List inst_list) throws IOException{
		//System.out.println(inst_list);
		File info=new File("dis-fb.txt"); 
		if(!info.exists())       
			info.createNewFile();
        FileOutputStream file=new FileOutputStream(info,true);
        PrintStream p=new PrintStream(file); 
		
		switch (type){
		case "J"://------------------------------J------------------------------
			String address=(String) inst_list.get(2);
			int add=Integer.valueOf(address);
			String index=(String) inst_list.get(0);
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+index+" J #"+address);
					
	        p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+index+" J #"+address);
			p.close();
			J(add);
			break;
		case "JR"://------------------------------JR------------------------------
			/*temp.add(String.valueOf(add_jr));
			temp.add("JR");
			temp.add(String.valueOf(jr_rs));
			 * */
			//PC ← rs
			String jr_rs=(String) inst_list.get(2);
			int rs=Integer.valueOf(jr_rs);
			String jradd=(String) inst_list.get(0);
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+jradd+" JR #"+jr_rs);
			
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+jradd+" JR #"+jr_rs);
			p.close();
			
			JR(rs);
			break;
		case "BEQ":
			/*temp.add(String.valueOf(add_beq));0
			temp.add("BEQ");1
			temp.add(String.valueOf(beq_rs));2
			temp.add(String.valueOf(beq_rt));3
			temp.add(String.valueOf(offset));4
			 * */
			//if rs = rt then branch
			//PC ← PC + target_offset
			String beq_add=(String) inst_list.get(0);
			String beq_rs=(String) inst_list.get(2);
			String beq_rt=(String) inst_list.get(3);
			String beq_offset=(String) inst_list.get(4);
			int beqrs=Integer.valueOf(beq_rs);
			int beqrt=Integer.valueOf(beq_rt);
			int beqoffset=Integer.valueOf(beq_offset);
			
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+beq_add+" BEQ "+"R"+beq_rs+", R"+beq_rt+", #"+beq_offset);
			
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+beq_add+" BEQ "+"R"+beq_rs+", R"+beq_rt+", #"+beq_offset);
			p.close();
			BEQ(beqrs,beqrt,beqoffset);
			break;
		case "BLTZ":
			/* temp.add(String.valueOf(add_bltz));
			 * temp.add("BLTZ");
			 * temp.add(String.valueOf(bltz_rs));
			 * temp.add(String.valueOf(bltz_offset));
			 * */
			String add_bltz=(String) inst_list.get(0);
			String bltz_rs=(String) inst_list.get(2);
			String bltz_offset=(String) inst_list.get(3);
			int bltzrs=Integer.valueOf(bltz_rs);
			int bltzoffset=Integer.valueOf(bltz_offset);
			//BLTZ rs, offset
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+add_bltz+"\t"+"BLTZ "+"R"+bltz_rs+", #"+bltz_offset);
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+add_bltz+"\t"+"BLTZ "+"R"+bltz_rs+", #"+bltz_offset);
			p.close();
			BLTZ(bltzrs,bltzoffset);
			break;
		case "BGTZ":
			String add_bgtz=(String) inst_list.get(0);
			String bgtz_rs=(String) inst_list.get(2);
			String bgtz_offset=(String) inst_list.get(3);
			int bgtzrs=Integer.valueOf(bgtz_rs);
			int bgtzoffset=Integer.valueOf(bgtz_offset);
			//BGTZ rs, offset
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+add_bgtz+"\t"+"BGTZ "+"R"+bgtz_rs+", #"+bgtz_offset);
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+add_bgtz+"\t"+"BGTZ "+"R"+bgtz_rs+", #"+bgtz_offset);
			p.close();
			BGTZ(bgtzrs,bgtzoffset);
					
			break;
			
		case "BREAK":
			String add_break=(String) inst_list.get(0);
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+add_break+" BREAK");
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+add_break+" BREAK");
			p.close();
			print_register();
			print_data();
			break;
		
		
		case "SW":
			/*temp.add(String.valueOf(add_sw));
			temp.add("SW");
			temp.add(String.valueOf(sw_base));
			temp.add(String.valueOf(sw_rt));
			temp.add(String.valueOf(sw_offset));
			 * */
			String add_sw=(String) inst_list.get(0);
			String sw_base=(String) inst_list.get(2);
			String sw_rt=(String) inst_list.get(3);
			String sw_offset=(String) inst_list.get(4);
			int swbase=Integer.valueOf(sw_base);
			int swrt=Integer.valueOf(sw_rt);
			int swoffset=Integer.valueOf(sw_offset);
			
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+add_sw+"\t"+"SW "+"R"+sw_rt+", "+sw_offset+"(R"+sw_base+")");
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+add_sw+"\t"+"SW "+"R"+sw_rt+", "+sw_offset+"(R"+sw_base+")");
			p.close();
			SW(swrt,swoffset,swbase);
			break;
		case "LW":
			String add_lw=(String) inst_list.get(0);
			String lw_base=(String) inst_list.get(2);
			String lw_rt=(String) inst_list.get(3);
			String lw_offset=(String) inst_list.get(4);
			int lwbase=Integer.valueOf(lw_base);
			int lwrt=Integer.valueOf(lw_rt);
			int lwoffset=Integer.valueOf(lw_offset);
			
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+add_lw+"\t"+"LW "+"R"+lw_rt+", "+lw_offset+"(R"+lw_base+")");
			
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+add_lw+"\t"+"LW "+"R"+lw_rt+", "+lw_offset+"(R"+lw_base+")");
			p.close();
			LW(lwrt,lwoffset,lwbase);
			break;
		 
		case "SLL":
			/*temp.add(String.valueOf(add_sll));
			temp.add("SLL");
			temp.add(String.valueOf(sll_rd));
			temp.add(String.valueOf(sll_rt));
			temp.add(String.valueOf(sll_sa));*/
			String add_sll=(String) inst_list.get(2);
			String sll_rd=(String) inst_list.get(2);
			String sll_rt=(String) inst_list.get(3);
			String sll_sa=(String) inst_list.get(4);
			int sllrd=Integer.valueOf(sll_rd);
			int sllrt=Integer.valueOf(sll_rt);
			int sllsa=Integer.valueOf(sll_sa);
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+add_sll+"\t"+"SLL "+"R"+sll_rd+", R"+sll_rt+", #"+sll_sa);
			
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+add_sll+"\t"+"SLL "+"R"+sll_rd+", R"+sll_rt+", #"+sll_sa);
			p.close();
			SLL(sllrd,sllrt,sllsa);
			break;
		case "SRL":
			String add_srl=(String) inst_list.get(0);
			String srl_rd=(String) inst_list.get(2);
			String srl_rt=(String) inst_list.get(3);
			String srl_sa=(String) inst_list.get(4);
			int srlrd=Integer.valueOf(srl_rd);
			int srlrt=Integer.valueOf(srl_rt);
			int srlsa=Integer.valueOf(srl_sa);
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+add_srl+"\t"+"SRL "+"R"+srl_rd+", R"+srl_rt+", #"+srl_sa);
			
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+add_srl+"\t"+"SRL "+"R"+srl_rd+", R"+srl_rt+", #"+srl_sa);
			p.close();
			SRL(srlrd,srlrt,srlsa);
			break;
		 
		case "SRA":
			String add_sra=(String) inst_list.get(0);
			String sra_rd=(String) inst_list.get(2);
			String sra_rt=(String) inst_list.get(3);
			String sra_sa=(String) inst_list.get(4);
			int srard=Integer.valueOf(sra_rd);
			int srart=Integer.valueOf(sra_rt);
			int srasa=Integer.valueOf(sra_sa);
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+add_sra+"\t"+"SRA "+"R"+sra_rd+", R"+sra_rt+", #"+sra_sa);
			
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+add_sra+"\t"+"SRA "+"R"+sra_rd+", R"+sra_rt+", #"+sra_sa);
			p.close();
			SRL(srard,srart,srasa);
			break;
			
		case "NOP":
			String add_nop=(String) inst_list.get(0);
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+add_nop+"\t"+"NOP");
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+add_nop+"\t"+"NOP");
			p.close();
			NOP();
			break;
									
		case "ADD":
			String add_add=(String) inst_list.get(0);
			String add_rd=(String) inst_list.get(2);
			String add_rs=(String) inst_list.get(3);
			String add_rt=(String) inst_list.get(4);
			int rdi=Integer.valueOf(add_rd);
			int rsi=Integer.valueOf(add_rs);
			int rti=Integer.valueOf(add_rt);
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+add_add+"\tADD "+"R"+rdi+", R"+rsi+", R"+rti);
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+add_add+"\tADD "+"R"+rdi+", R"+rsi+", R"+rti);
			p.close();
			ADD(rdi,rsi,rti);
			break;
		case "SUB":
			/*temp.add(String.valueOf(add_sub));
			temp.add("SUB");
			temp.add(String.valueOf(srd));
			temp.add(String.valueOf(srs));
			temp.add(String.valueOf(srt));*/
			String sub_add=(String) inst_list.get(0);
			String srd=(String) inst_list.get(2);
			String srs=(String) inst_list.get(3);
			String srt=(String) inst_list.get(4);
			int srdi=Integer.valueOf(srd);
			int srsi=Integer.valueOf(srs);
			int srti=Integer.valueOf(srt);
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+sub_add+"\tSUB "+"R"+srdi+", R"+srsi+", R"+srti);
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+sub_add+"\tSUB "+"R"+srdi+", R"+srsi+", R"+srti);
			p.close();
			SUB(srdi,srsi,srti);			
			break;
		
		case "MUL":
			/*temp.add(String.valueOf(add_mul));
			temp.add("MUL");
			temp.add(String.valueOf(mrd));
			temp.add(String.valueOf(mrs));
			temp.add(String.valueOf(mrt));
			 * */
			String mul_add=(String) inst_list.get(0);
			String mrd=(String) inst_list.get(2);
			String mrs=(String) inst_list.get(3);
			String mrt=(String) inst_list.get(4);
			int mrdi=Integer.valueOf(mrd);
			int mrsi=Integer.valueOf(mrs);
			int mrti=Integer.valueOf(mrt);
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+mul_add+"\tMUL "+"R"+mrdi+", R"+mrsi+", R"+mrti);
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+mul_add+"\tMUL "+"R"+mrdi+", R"+mrsi+", R"+mrti);
			p.close();
			
			MUL(mrdi,mrsi,mrti);
			break;
		
		case "AND":
			/*temp.add(String.valueOf(add_and));
			temp.add("AND");
			temp.add(String.valueOf(ard));
			temp.add(String.valueOf(ars));
			temp.add(String.valueOf(art));*/
			String and_add=(String) inst_list.get(0);
			String ard=(String) inst_list.get(2);
			String ars=(String) inst_list.get(3);
			String art=(String) inst_list.get(4);
			int ardi=Integer.valueOf(ard);
			int arsi=Integer.valueOf(ars);
			int arti=Integer.valueOf(art);
			
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+and_add+"\tAND "+"R"+ardi+", R"+arsi+", R"+arti);
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+and_add+"\tAND "+"R"+ardi+", R"+arsi+", R"+arti);
			p.close();
			AND(ardi,arsi,arti);			
			break;
			
		case "OR":
			/*temp.add(String.valueOf(add_and));
			temp.add("OR");
			temp.add(String.valueOf(ard));
			temp.add(String.valueOf(ars));
			temp.add(String.valueOf(art));*/
			String or_add=(String) inst_list.get(0);
			String ord=(String) inst_list.get(2);
			String ors=(String) inst_list.get(3);
			String ort=(String) inst_list.get(4);
			int ordi=Integer.valueOf(ord);
			int orsi=Integer.valueOf(ors);
			int orti=Integer.valueOf(ort);
			
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+or_add+"\tOR "+"R"+ordi+", R"+orsi+", R"+orti);
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+or_add+"\tOR "+"R"+ordi+", R"+orsi+", R"+orti);
			p.close();
			OR(ordi,orsi,orti);			
			break;
			
		case "XOR":
			String xor_add=(String) inst_list.get(0);
			String xrd=(String) inst_list.get(2);
			String xrs=(String) inst_list.get(3);
			String xrt=(String) inst_list.get(4);
			int xrdi=Integer.valueOf(xrd);
			int xrsi=Integer.valueOf(xrs);
			int xrti=Integer.valueOf(xrt);
			
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+xor_add+"\tOR "+"R"+xrdi+", R"+xrsi+", R"+xrti);
			
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+xor_add+"\tOR "+"R"+xrdi+", R"+xrsi+", R"+xrti);
			p.close();
			XOR(xrdi,xrsi,xrti);			
			break;
		case "NOR":
			String nor_add=(String) inst_list.get(0);
			String nrd=(String) inst_list.get(2);
			String nrs=(String) inst_list.get(3);
			String nrt=(String) inst_list.get(4);
			int nrdi=Integer.valueOf(nrd);
			int nrsi=Integer.valueOf(nrs);
			int nrti=Integer.valueOf(nrt);
			
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+nor_add+"\tOR "+"R"+nrdi+", R"+nrsi+", R"+nrti);
			
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+nor_add+"\tOR "+"R"+nrdi+", R"+nrsi+", R"+nrti);
			p.close();
			NOR(nrdi,nrsi,nrti);
			break;
		case "SLT":
			String slt_add=(String) inst_list.get(0);
			String slrd=(String) inst_list.get(2);
			String slrs=(String) inst_list.get(3);
			String slrt=(String) inst_list.get(4);
			int slrdi=Integer.valueOf(slrd);
			int slrsi=Integer.valueOf(slrs);
			int slrti=Integer.valueOf(slrt);
			
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+slt_add+"\tOR "+"R"+slrdi+", R"+slrsi+", R"+slrti);
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+slt_add+"\tOR "+"R"+slrdi+", R"+slrsi+", R"+slrti);
			p.close();
			SLT(slrdi,slrsi,slrti);
			break;	
		case "ADDI":
			/*temp.add(String.valueOf(add_addi));
			temp.add("ADDI");
			temp.add(String.valueOf(addi_rt));
			temp.add(String.valueOf(addi_rs));
			temp.add(String.valueOf(immediate));*/
			String addi_add=(String) inst_list.get(0);
			String addi_rt=(String) inst_list.get(2);
			String addi_rs=(String) inst_list.get(3);
			String addi_im=(String) inst_list.get(4);
			int addirti=Integer.valueOf(addi_rt);
			int addirsi=Integer.valueOf(addi_rs);
			int addiim=Integer.valueOf(addi_im);
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+addi_add+"\tADDI "+"R"+addi_rt+", R"+addi_rs+", #"+addiim);
			
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+addi_add+"\tADDI "+"R"+addi_rt+", R"+addi_rs+", #"+addiim);
			p.close();
			ADDI(addirti,addirsi,addiim);
			break;
			
		case "ANDI":
			String andi_add=(String) inst_list.get(0);
			String andi_rt=(String) inst_list.get(2);
			String andi_rs=(String) inst_list.get(3);
			String andi_im=(String) inst_list.get(4);
			int andirti=Integer.valueOf(andi_rt);
			int andirsi=Integer.valueOf(andi_rs);
			int andiim=Integer.valueOf(andi_im);
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+andi_add+"\tADDI "+"R"+andi_rt+", R"+andi_rs+", #"+andiim);
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+andi_add+"\tADDI "+"R"+andi_rt+", R"+andi_rs+", #"+andiim);
			p.close();
			ANDI(andirti,andirsi,andiim);
			break;
			
		case "ORI":
			String ori_add=(String) inst_list.get(0);
			String ori_rt=(String) inst_list.get(2);
			String ori_rs=(String) inst_list.get(3);
			String ori_im=(String) inst_list.get(4);
			int orirti=Integer.valueOf(ori_rt);
			int orirsi=Integer.valueOf(ori_rs);
			int oriim=Integer.valueOf(ori_im);
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+ori_add+"\tADDI "+"R"+ori_rt+", R"+ori_rs+", #"+ori_im);
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+ori_add+"\tADDI "+"R"+ori_rt+", R"+ori_rs+", #"+ori_im);
			p.close();
			ORI(orirti,orirsi,oriim);
			break;
		case "XORI":
			String xori_add=(String) inst_list.get(0);
			String xori_rt=(String) inst_list.get(2);
			String xori_rs=(String) inst_list.get(3);
			String xori_im=(String) inst_list.get(4);
			int xorirti=Integer.valueOf(xori_rt);
			int xorirsi=Integer.valueOf(xori_rs);
			int xoriim=Integer.valueOf(xori_im);
			System.out.println("--------------------");
			System.out.println("Cycle:"+Cycle+"\t"+xori_add+"\tADDI "+"R"+xori_rt+", R"+xori_rs+", #"+xori_im);
			
			p.println("--------------------");
			p.println("Cycle:"+Cycle+"\t"+xori_add+"\tADDI "+"R"+xori_rt+", R"+xori_rs+", #"+xori_im);
			p.close();
			XORI(xorirti,xorirsi,xoriim);
			break;
		
		}	
		
	}

	//--------------------------------------CATAGORY-1------------------------------
	public static void J(int instr_index) throws IOException	{
		//1.print present register and data
		//2.jump to instruction whose address is instr_index 
		
		print_register();
		print_data();
		Cycle++;
		pc=instr_index;
		run_add(pc);
		
	}	
	public static void JR(int rs) throws IOException{
		//跳转到寄存器rs里的地址
		int add=registers.get(rs);
		
		print_register();
		print_data();
		Cycle++;
		pc=add;
		run_add(add);	
	}
	public static void BEQ(int rs,int rt,int offset) throws IOException{
		//if rs==rt pc=pc+4+offset
		int beq_rs=registers.get(rs);
		int beq_rt=registers.get(rt);
		print_register();
		print_data();
		if(beq_rs==beq_rt){
			pc=pc+4+offset;
			Cycle++;
			run_add(pc);
		}
		else {
			pc=pc+4;
			Cycle++;
			run_add(pc);
		}
		
	}
	public static void BLTZ(int rs,int offset) throws IOException{
		//BLTZ rs, offset
		//if rs < 0 then pc=pc+4+offset
		int bltz_rs=registers.get(rs);
		print_register();
		print_data();
		if(bltz_rs<0){
			pc=pc+4+offset;
			Cycle++;
			run_add(pc);
		}
		else {
			pc=pc+4;
			Cycle++;
			run_add(pc);
		}
	}	 
	public static void BGTZ(int rs,int offset) throws IOException{
		//BGTZ rs, offset
		//if rs < 0 then pc=pc+4+offset
		int bgtz_rs=registers.get(rs);
		print_register();
		print_data();		
		if(bgtz_rs>0){
			pc=pc+4+offset;
			Cycle++;
			run_add(pc);
		}
		else {
			pc=pc+4;
			Cycle++;
			run_add(pc);
		}
	}
	public static void SW(int rt,int offset,int base) throws IOException{
	//memory[base+offset] ← rt
	//把地址是地址是offset+r(base) 里的值改为r(rt)
		int r_rt=registers.get(rt);		
		int r_base=registers.get(base);
		int memory =offset+r_base;
		String rep=String.valueOf(r_rt);
		int fline=line_code.size();
		for(int i=0;i<data.size();i++){
			String address=(String) data.get(i+fline).get(0);
			int add=Integer.valueOf(address);
			if(add==memory){
				data.get(i+fline).set(1, rep);
				break;
			}
		}
		print_register();
		print_data();
		pc=pc+4;
		Cycle++;
		run_add(pc);
	}
	public static void LW(int rt,int offset,int base) throws IOException{
	//rt ← memory[base+offset]
	//把r(rt)   改为地址是地址是offset+r(base) 里的值  	
		int r_base=registers.get(base);
		int add1 =offset+r_base;
		int fline=line_code.size();
		for(int i=0;i<data.size();i++){			 
			String address=(String) data.get(i+fline).get(0);
			int add=Integer.valueOf(address);
			//System.out.println(add);
			if(add==add1){	 
				String val=(String) data.get(i+fline).get(1);
				int value=Integer.valueOf(val);
				registers.set(rt, value);
				break;
			}
		}
		print_register();
		print_data();
		pc=pc+4;
		Cycle++;
		run_add(pc);
	}
	public static void SLL(int rd,int rt,int sa) throws IOException{
	//SLL rd, rt, sa
	//rd ← rt << sa 逻辑左移
		//if(sa!=0){
		
		if((rd==0)&&(rt==0)&&(sa==0)){
			print_register();
			print_data();
			pc=pc+4;
			Cycle++;
			run_add(pc);
		}
		else{
		int rrt=registers.get(rt);
		int re=rrt << sa;
		registers.set(rd, re);
		print_register();
		print_data();
		pc=pc+4;
		Cycle++;
		run_add(pc);
		}
	}
	public static void SRL(int rd,int rt,int sa) throws IOException{
	//SRL rd, rt, sa  
	//rd ← rt >> sa 逻辑右移
		int rrt=registers.get(rt);
		int re=rrt>>>sa;		//!!!!!!!!!!!!!!!!!!!!!!
		registers.set(rd, re);
		print_register();
		print_data();
		pc=pc+4;
		Cycle++;
		run_add(pc);
	}
	public static void SRA(int rd,int rt,int sa) throws IOException{//????
		//SRL rd, rt, sa
		//rd ← rt >> sa 算术右移
		int rrt=registers.get(rt);
		int re=rrt>>sa;
		registers.set(rd, re);
		print_register();
		print_data();
		pc=pc+4;
		Cycle++;
		run_add(pc);
	}
	public static void NOP() throws IOException{
		Cycle++;
		print_register();
		print_data();
		pc=pc+4;
		run_add(pc);
	}
	
	//--------------------------------------CATAGORY-2------------------------------
	//--------------------------------------ADD-------------------------------------
	public static void ADD(int rd,int rs,int rt) throws IOException{	
		//rs rt rd 00000 
		//AND rd, rs, rt
		//更新rd寄存器的值为rs寄存器，rt寄存器值的和
		int rsv,rtv;
		rsv=registers.get(rs);
		rtv=registers.get(rt);	 
		registers.set(rd, rtv+rsv);
		
		print_register();
		print_data();
		Cycle++;
		pc=pc+4;
		run_add(pc);
		
		 
	}
	//--------------------------------------SUB-------------------------------------
	public static void SUB(int rd,int rs,int rt) throws IOException{	
		//rs rt rd 00000 
		//SUB rd, rs, rt
		//rd=rs-rt
		//更新rd寄存器的值为rs寄存器与rt寄存器值的差
		int rsv,rtv;
		rsv=registers.get(rs);
		rtv=registers.get(rt);	 
		registers.set(rd, rsv-rtv);
		print_register();
		print_data();
		pc=pc+4;
		Cycle++;
		run_add(pc);	 
	}
	//--------------------------------------MUL-------------------------------------
	public static void MUL(int rd,int rs,int rt) throws IOException{	
		//rs rt rd 00000 
		//MUL rd, rs, rt
		//rd=rs*rt
		//更新rd寄存器的值为rs寄存器与rt寄存器值的积
		int rsv,rtv;
		rsv=registers.get(rs);
		rtv=registers.get(rt);	 
		registers.set(rd, rtv*rsv);
		print_register();
		print_data();
		pc=pc+4;
		Cycle++;
		run_add(pc);	 
	}
	//--------------------------------------AND-------------------------------------
	public static void AND(int rd,int rs,int rt) throws IOException{	
			//rs rt rd 00000 
			//AND rd, rs, rt
			//rd=rs AND rt
			//更新rd寄存器的值为rs寄存器与rt寄存器值的与
			int rsv,rtv;
			rsv=registers.get(rs);
			rtv=registers.get(rt);	 
			registers.set(rd, rtv & rsv);
			print_register();
			print_data();
			pc=pc+4;
			Cycle++;
			run_add(pc);	 
		}
	//--------------------------------------OR-------------------------------------
	public static void OR(int rd,int rs,int rt) throws IOException{	
			//rs rt rd 00000 
			//OR rd, rs, rt
			//rd=rs OR rt
			//更新rd寄存器的值为rs寄存器与rt寄存器值的或
			int rsv,rtv;
			rsv=registers.get(rs);
			rtv=registers.get(rt);	 
			registers.set(rd, rtv | rsv);
			print_register();
			print_data();
			pc=pc+4;
			Cycle++;
			run_add(pc);	 
		}
	//--------------------------------------XOR-------------------------------------
	public static void XOR(int rd,int rs,int rt) throws IOException{	
			//rs rt rd 00000 
			//XOR rd, rs, rt
			//rd=rs XOR rt
			//更新rd寄存器的值为rs寄存器与rt寄存器值的异或
			int rsv,rtv;
			rsv=registers.get(rs);
			rtv=registers.get(rt);	 
			registers.set(rd, rtv ^ rsv);
			print_register();
			print_data();
			pc=pc+4;
			Cycle++;
			run_add(pc); 	 
		}
	//--------------------------------------NOR-------------------------------------
	public static void NOR(int rd,int rs,int rt) throws IOException{	
			//rs rt rd 00000 
			//NOR rd, rs, rt
			//rd=rs NOR rt
			//更新rd寄存器的值为rs寄存器与rt寄存器值的或非
			int rsv,rtv;
			rsv=registers.get(rs);
			rtv=registers.get(rt);	 
			registers.set(rd, ~(rtv | rsv));
			print_register();
			print_data();
			pc=pc+4;
			Cycle++;
			run_add(pc); 	 
		}
	//--------------------------------------SLT-------------------------------------
	public static void SLT(int rd,int rs,int rt) throws IOException{	
			//rs rt rd 00000 
			//SLT rd, rs, rt
			//rd=1 (rs < rt) | 0 (rs>rt)
			int rsv,rtv;
			rsv=registers.get(rs);
			rtv=registers.get(rt);
			if(rsv<rtv)
				registers.set(rd, 1);
			else
				registers.set(rd, 0);	
			print_register();
			print_data();
			pc=pc+4;
			Cycle++;
			run_add(pc);	 
		}
	//--------------------------------------ADDI-------------------------------------
	public static void ADDI(int rt,int rs,int immediate) throws IOException{	
			//ADDI rt, rs, immediate 
			//rt ← rs + immediate			 
			int rsv;
			rsv=registers.get(rs);
			registers.set(rt, rsv+immediate);	
			print_register();
			print_data();
			pc=pc+4;
			Cycle++;
			run_add(pc);	 
		}	
	//--------------------------------------ANDI-------------------------------------
	public static void ANDI(int rt,int rs,int immediate) throws IOException{	
			//ANDI rt, rs, immediate 
			//rt ← rs AND immediate		 
			int rsv;
			rsv=registers.get(rs);
			registers.set(rt, rsv & immediate);	
			print_register();
			print_data();
			pc=pc+4;
			Cycle++;
			run_add(pc);	 
		}	
	//--------------------------------------ORI-------------------------------------
	public static void ORI(int rt,int rs,int immediate) throws IOException{	
			//ORI rt, rs, immediate 
			//rt ← rs or immediate	 
			int rsv;
			rsv=registers.get(rs);
			registers.set(rt, rsv | immediate);	
			print_register();
			print_data();
			pc=pc+4;
			Cycle++;
			run_add(pc);	 
		}	
	//--------------------------------------XORI-------------------------------------
	public static void XORI(int rt,int rs,int immediate) throws IOException{	
			//XORI rt, rs, immediate 
			//rt ← rs XOR immediate	 
			int rsv;
			rsv=registers.get(rs);
			registers.set(rt, rsv ^ immediate);	
			
			print_register();
			print_data();
			pc=pc+4;
			Cycle++;
			run_add(pc);	 
		}	
}
