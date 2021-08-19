package com.takeoff.service;

import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeoff.model.DisplayDetailsDTO;
import com.takeoff.model.StructureDTO;
import com.takeoff.repository.CustomerDetailsRepository;
import com.takeoff.repository.CustomerMappingRepository;

@Service
public class DisplayService {
	
	
	@Autowired
	CustomerMappingRepository customerMappingRepository;
	
	@Autowired
	CustomerDetailsRepository customerDetailsRepository;
	
	public String parse1(DefaultMutableTreeNode root)
	{
		String ret="",text=root.toString().substring(1),id=root.toString().substring(1);

	if(root.toString().startsWith("D"))
		text="<font color=blue><b>"+text+"</b></font>";
	else if(root.toString().startsWith("I"))
		text="<font color=brown><b>["+text+"]</b></font>";
	else if(root.toString().startsWith("R"))
		text="<font color=red><b>("+text+")</b></font>";

	if(root.isLeaf())
	ret=(" { \"title\": \""+text+"\", \"key\": \""+id+"\",\"isLeaf\": true },");
	else
		{
		ret="{\"title\": \""+text+"\", \"key\": \""+id+"\", \"children\": [";
	for(int i=0;i<root.getChildCount();i++)
		
		ret+=parse1((DefaultMutableTreeNode)root.getChildAt(i));
		
		
	ret+="]},";
		}

	return ret;
	}
	
	public StructureDTO getTreeStructure(Integer type)
	{
		Long rootCustomerId=customerMappingRepository.getRootUserId();
		Double amount = customerDetailsRepository.findByUserId(rootCustomerId).get().getWalletAmount();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("D"+rootCustomerId+" ("+amount+")");
		getTreeStructure(root);
		
		
		 JTree jt=new JTree(root);  
		 DefaultMutableTreeNode root1 = (DefaultMutableTreeNode)jt.getModel().getRoot();
		 
		 String structure="";
		 
		if(type == 0) 
			structure = "<ul>"+parse(root1)+"</ul><br/>";
		else
			structure = ("["+parse1(root1)+"]").replace(",]","]");
		
		System.out.println(structure);
		
		StructureDTO structureDTO = new StructureDTO(structure, type);
		return structureDTO;
		
		
	}
	
	public String parse(DefaultMutableTreeNode root)
	{
		String ret="",color="";

	if(root.toString().startsWith("D"))
		color="blue";
	else if(root.toString().startsWith("I"))
		color="brown";
	else if(root.toString().startsWith("R"))
		color="red";

	if(root.isLeaf())
	ret=("<li><span class='tf-nc'><font color='"+color+"'><b>"+root.toString().substring(1)+"</b></font></span></li>");
	else
		{
		ret="<li><span class='tf-nc'><font color='"+color+"'><b>"+root.toString().substring(1)+"</b></font></span><ul>";
	for(int i=0;i<root.getChildCount();i++)
		
		ret+=parse((DefaultMutableTreeNode)root.getChildAt(i));
		
		
	ret+="</ul></li>";
		}

	return ret;
	}
	
	public void getTreeStructure(DefaultMutableTreeNode root)
	{
		System.out.println(root);
		Long rootCustomerId = Long.valueOf(root.toString().substring(1,6));
		List<DisplayDetailsDTO> children = customerMappingRepository.getTreeStructure(rootCustomerId);
		
		for(int i=0;i<children.size();i++)
		{
		
		Long customerId=children.get(i).getCustomerId();
		Long refererId=children.get(i).getRefererId();
		Long parentId=children.get(i).getParentId();
		
		System.out.println(customerId+" "+refererId+" "+parentId);
		
		Double amount = customerDetailsRepository.findByUserId(customerId).get().getWalletAmount();
		
		String status="I";
		if(rootCustomerId.equals(refererId))
			{
			if(!(rootCustomerId.equals(parentId)))
				status="R";
			else
			status="D";
			}
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(status+""+customerId+" ("+amount+")");
		root.add(node);
		if(!(status.equals("R")))
			getTreeStructure(node);
		}
	}

}
