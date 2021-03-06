package gui;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import code.Person;
import code.SetBgrImg;
import code.SetChaImg;
import code.hero;
import code.heroine;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

/*
 * This class is mainly looked talking area.
 * Separate each image call, include layer.
 * Coded by namori.
 */

public class TalkPan {

	private SetBgrImg sbi;
	private SetChaImg sci;
	private JPanel mainPane;
	private JPanel textPan;
	private JLayeredPane lp;
	private JTextArea ta;
	private int chaCnt;
	private int bgrCnt;
	public int count = 0;

	public JPanel getMain() {
		return mainPane;
	}

	public TalkPan() {
		mainPane = new JPanel();
		lp = new JLayeredPane();
		LayoutSet();
	}

	private void LayoutSet() {
		mainPane.setLayout(null);

		SetBgr("./pics/cafe.jpg"); // 이거 초기값을 컨스트럭터에서 받게 할까??
		SetCha("./pics/fairy.png");

		SetText();
		MakeBtn();
		lp.setBounds(0, 0, Stat.getFramewidth(), Stat.getFrameheight());
		lp.add(textPan, JLayeredPane.MODAL_LAYER);

		lp.setVisible(true);
		mainPane.setVisible(true);
		mainPane.add(lp);
	}

	private void SetBgr(String file) {
		if (bgrCnt != 0)
			lp.remove(sbi);

		sbi = null;
		try {
			sbi = new SetBgrImg(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		sbi.setBounds(0, 0, Stat.getFramewidth(), Stat.getFrameheight());
		// sbi.repaint();
		lp.add(sbi, JLayeredPane.DEFAULT_LAYER);

		//System.out.println("배경이미지 설정: " + file + sbi.getWidth());
		bgrCnt++;
	}

	private void SetCha(String file) {
		if (chaCnt != 0)
			lp.remove(sci);

		sci = null;
		try {
			sci = new SetChaImg(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		int ChaH = sci.getImg().getHeight(null);
		//System.out.println(ChaH);
		int ChaW = sci.getImg().getWidth(null);
		//System.out.println(ChaW);

		sci.setBounds((Stat.getFramewidth() - ChaW) / 2, (Stat.getFrameheight() - ChaH) / 3, ChaW, ChaH);
		sci.setOpaque(false);
		// sci.repaint();
		lp.add(sci, JLayeredPane.PALETTE_LAYER);
		chaCnt++;
	}

	private void SetText() {
		textPan = new JPanel();
		textPan.setLayout(new BorderLayout());
		ta = new JTextArea();
		ta.setEditable(false);
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		ta.setText("Hello my name is Fairy.\nDo you know how to PLAY?\nPress NEXT button rightside.");
		JScrollPane sp = new JScrollPane(ta);
		textPan.add(sp);
		textPan.setOpaque(true);
		textPan.setBounds((Stat.getFramewidth() - 720) / 2, 500, 720, 180);
	}

	private void MakeBtn() {
		JButton nextBtn = new JButton("NEXT");

		nextBtn.setOpaque(true);
		textPan.add(nextBtn, BorderLayout.EAST);

		ArrayList<String> back = new ArrayList<String>();

		back.add("./pics/School.jpg");
		back.add("./pics/bench.jpg");
		back.add("./pics/sunyu.jpg");
		back.add("./pics/fleet.jpg");
		back.add("./pics/Library.jpg");
		back.add("./pics/place.png");
		back.add("./pics/Library.jpg");
		back.add("./pics/bench.jpg");
		back.add("./pics/Library.jpg");
		back.add("./pics/School.jpg");
		back.add("./pics/cafe.jpg");

		Person p = new hero();
		p.talking();
		// 남자파일 이름p.setFileName(");
		p.setFileName("./pics/konan.png");
		Person pe = new heroine();
		pe.talking();
		pe.setFileName("./pics/karen2.png");

		// SetBgr("./pics/namae.png");

		nextBtn.addActionListener(new ActionListener() {
			int asdf = 0;
			public void actionPerformed(ActionEvent e) {

				// contentPane.setVisible(false); //다음 프레임으로 넘어가기 위한 코드
				//System.out.println("Change Will be soon");

				SetCha(p.getFileName());
				//System.out.println(p.getFileName());

				String tmp = p.Dialogue();
if(tmp != null) {
				if (tmp.equals("@")) {
					ExamPan ep = new ExamPan(Stat.getPronum());
					Stat.setPronum(Stat.getPronum() + 1);
					PanelChange.convert(ep.getMain());
					tmp = p.Dialogue();
					if (tmp.equals("#")) {
						SetCha(pe.getFileName());
						ta.setText(pe.Dialogue());
					} else
						ta.setText(tmp);
					SetBgr(back.get(count++));
				} else if (tmp.equals("#")) {
					SetCha(pe.getFileName());
					ta.setText(pe.Dialogue());
				} else
					ta.setText(tmp);
}
else {
	//System.out.println("EALJKSDHKJASDHLKAJSHLKJASHFLKJASF");
	ta.setText("You studied very hard.\nHow about coding yourself?");
	nextBtn.setText("Sure!!");
	
	if(asdf != 0)
	{
		CodingPan co = new CodingPan();
		PanelChange.convert(co.getMain());
	}
	asdf++;
}
				//System.out.println(tmp);

			}
		});
	}
}
