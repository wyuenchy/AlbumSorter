import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Index extends JFrame {
	public static JLabel lblStatus;
	private JPanel contentPane;
	protected File selectedFile;
	//create a program entry point for the program, this is the first part of the program to start
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Index frame = new Index();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void fin() {
		lblStatus.setText("Done");
	}
	
	public Index() {
		setTitle("Lossless Abum Sorter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblsource = new JLabel("Music/");
		lblsource.setBounds(6, 44, 688, 16);
		contentPane.add(lblsource);
	
		JButton btnDetailedTable = new JButton("Select Source");
		btnDetailedTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//create a button listener to open table listener if the user click the button to use their own file.
				  JFileChooser fileChooser = new JFileChooser();
				  fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			        int returnValue = fileChooser.showOpenDialog(null);
			        if (returnValue == JFileChooser.APPROVE_OPTION) {
			          File selectedFile = fileChooser.getSelectedFile();
			          lblsource.setText(selectedFile.getAbsolutePath());
			        }
			        
			      }
			    });
				 
			
		
		btnDetailedTable.setBounds(6, 72, 164, 29);
		contentPane.add(btnDetailedTable);
		
		JLabel lblYouAreChoosing = new JLabel("Source Library");
		lblYouAreChoosing.setBounds(6, 16, 95, 16);
		contentPane.add(lblYouAreChoosing);
		
		JLabel lblDest = new JLabel("Music/");
		lblDest.setBounds(6, 142, 249, 16);
		contentPane.add(lblDest);
		
		JLabel lblDestination = new JLabel("Destination");
		lblDest.setBounds(6, 114, 688, 16);
		contentPane.add(lblDest);
		
		JButton btnDest = new JButton("Select Destination");
		btnDest.setBounds(6, 171, 164, 29);
		contentPane.add(btnDest);
		
		btnDest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//create a button listener to open table listener if the user click the button to use their own file.
				  JFileChooser fileChooser = new JFileChooser();
				  fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			        int returnValue = fileChooser.showOpenDialog(null);
			        if (returnValue == JFileChooser.APPROVE_OPTION) {
			          File destSelectedFile = fileChooser.getSelectedFile();
			          lblDest.setText(destSelectedFile.getAbsolutePath());
			        }
			        
			      }
			    });
		
		JButton btnStart = new JButton("Start Sorting");
		btnStart.setBounds(559, 171, 135, 29);
		contentPane.add(btnStart);
		
		lblStatus = new JLabel("");
		lblStatus.setBounds(569, 206, 125, 16);
		contentPane.add(lblStatus);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String source = lblsource.getText();
				String dest = lblDest.getText();
				try {
					sortingAlgo.algo(source, dest);
				} catch (CannotReadException | IOException | TagException | ReadOnlyFileException
						| InvalidAudioFrameException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}	
}

