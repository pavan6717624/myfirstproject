package com.takeoff.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.takeoff.domain.CustomerDetails;
import com.takeoff.model.DisplayDetailsDTO;
import com.takeoff.repository.CustomerDetailsRepository;
import com.takeoff.repository.CustomerMappingRepository;

@Service
public class DisplayService {
	
	
	@Autowired
	CustomerMappingRepository customerMappingRepository;
	
	@Autowired
	CustomerDetailsRepository customerDetailsRepository;
	
	public String getTreeStructure()

    {

                    System.out.println("Controller Service");

                    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                   

                    List<DisplayDetailsDTO> allNodes = customerMappingRepository.getTreeStructureNew();
                    
                    
                    Long parentNode = allNodes.stream().filter(n -> n.getCustomerId().equals(Long.valueOf(userDetails.getUsername()))).collect(Collectors. toList()).get(0).getParentId();
                    
                    CustomerDetails parent = customerDetailsRepository.findByUserId(parentNode).get();
                    
                    String parentName = parent.getUser().getName();
                    String referalCode = parent.getReferCode();

                    String ret="";

                   

    ret =      getStructure(allNodes, Arrays.asList(Long.valueOf(userDetails.getUsername())),Long.valueOf(userDetails.getUsername()));

   
    if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("Admin")))
    	return ret;
    else
    	 return "<ul><li><span class='tf-nc'><font color=blue><b>"+referalCode+"<br>"+parentName+"</b></font></span>"+ret+"</li></ul>";
    	



                   

    }



    public String getStructure(List<DisplayDetailsDTO> allNodes, List<Long> nodes, Long parentId)

    {

                    String ret="";

                    System.out.println("Controller Service Structure");

                    List<DisplayDetailsDTO> displayNodes= allNodes.stream().filter(n -> nodes.contains(n.getCustomerId())).sorted(Comparator.comparing(DisplayDetailsDTO::getCustomerId)).collect(Collectors. toList());

   

                    if(displayNodes.size() > 0)

                                    ret+="<ul>";



                    for(int i=0;i<displayNodes.size();i++)

                    {

                                    Long customerId = displayNodes.get(i).getCustomerId();
                                    String name=displayNodes.get(i).getName();
                                    String referralCode=displayNodes.get(i).getReferCode();


                                   

                                    String color="blue";

                                                                   

                                   

                                    if(displayNodes.get(i).getRefererId().equals(parentId) &&  !displayNodes.get(i).getParentId().equals(parentId))

                                                    color="red";

                                    else if(!displayNodes.get(i).getRefererId().equals(parentId) &&  displayNodes.get(i).getParentId().equals(parentId))

                                                    color="brown";

                                    //System.out.println(userNodes.get(i).getParentId()+" "+userNodes.get(i).getRefererId()+" "+customerId);

                                   ret += "<li><span class='tf-nc'><font color="+color+"><b>"+referralCode+"<hr>"+name+"</b></font></span>";

                                   

                                   if(!color.equals("red"))

                                    ret+=getStructure(allNodes,allNodes.stream().filter(n -> !(n.getCustomerId().equals(customerId)) && (n.getParentId().equals(customerId) || n.getRefererId().equals(customerId))).map(DisplayDetailsDTO::getCustomerId).collect(Collectors.toList()),customerId);

                                   

                                   

                   

                   

                                    ret+= "</li>";

                    }

                   

                    if(displayNodes.size() > 0)

                                    ret+="</ul>";

                   

                    return ret;

    }
	
//	public String parse1(DefaultMutableTreeNode root)
//	{
//		String ret="",text=root.toString().substring(1),id=root.toString().substring(1);
//
//	if(root.toString().startsWith("D"))
//		text="<font color=blue><b>"+text+"</b></font>";
//	else if(root.toString().startsWith("I"))
//		text="<font color=brown><b>["+text+"]</b></font>";
//	else if(root.toString().startsWith("R"))
//		text="<font color=red><b>("+text+")</b></font>";
//
//	if(root.isLeaf())
//	ret=(" { \"title\": \""+text+"\", \"key\": \""+id+"\",\"isLeaf\": true },");
//	else
//		{
//		ret="{\"title\": \""+text+"\", \"key\": \""+id+"\", \"children\": [";
//	for(int i=0;i<root.getChildCount();i++)
//		
//		ret+=parse1((DefaultMutableTreeNode)root.getChildAt(i));
//		
//		
//	ret+="]},";
//		}
//
//	return ret;
//	}
	
//	public StructureDTO getTreeStructure(Integer type)
//	{
//		Long rootCustomerId=customerMappingRepository.getRootUserId();
//		Double amount = customerDetailsRepository.findByUserId(rootCustomerId).get().getWalletAmount();
//		DefaultMutableTreeNode root = new DefaultMutableTreeNode("D"+rootCustomerId+" ("+amount+")");
//		getTreeStructure(root);
//		
//		
//		 JTree jt=new JTree(root);  
//		 DefaultMutableTreeNode root1 = (DefaultMutableTreeNode)jt.getModel().getRoot();
//		 
//		 String structure="";
//		 
//		if(type == 0) 
//			structure = "<ul>"+parse(root1)+"</ul><br/>";
//		else
//			structure = ("["+parse1(root1)+"]").replace(",]","]");
//		
//		System.out.println(structure);
//		
//		StructureDTO structureDTO = new StructureDTO(structure, type);
//		return structureDTO;
//		
//		
//	}
//	
//	public String parse(DefaultMutableTreeNode root)
//	{
//		String ret="",color="";
//
//	if(root.toString().startsWith("D"))
//		color="blue";
//	else if(root.toString().startsWith("I"))
//		color="brown";
//	else if(root.toString().startsWith("R"))
//		color="red";
//
//	if(root.isLeaf())
//	ret=("<li><span class='tf-nc'><font color='"+color+"'><b>"+root.toString().substring(1)+"</b></font></span></li>");
//	else
//		{
//		ret="<li><span class='tf-nc'><font color='"+color+"'><b>"+root.toString().substring(1)+"</b></font></span><ul>";
//	for(int i=0;i<root.getChildCount();i++)
//		
//		ret+=parse((DefaultMutableTreeNode)root.getChildAt(i));
//		
//		
//	ret+="</ul></li>";
//		}
//
//	return ret;
//	}
//	
//	public void getTreeStructure(DefaultMutableTreeNode root)
//	{
//		System.out.println(root);
//		Long rootCustomerId = Long.valueOf(root.toString().substring(1,6));
//		List<DisplayDetailsDTO> children = customerMappingRepository.getTreeStructure(rootCustomerId);
//		
//		for(int i=0;i<children.size();i++)
//		{
//		
//		Long customerId=children.get(i).getCustomerId();
//		Long refererId=children.get(i).getRefererId();
//		Long parentId=children.get(i).getParentId();
//		
//		System.out.println(customerId+" "+refererId+" "+parentId);
//		
//		Double amount = customerDetailsRepository.findByUserId(customerId).get().getWalletAmount();
//		
//		String status="I";
//		if(rootCustomerId.equals(refererId))
//			{
//			if(!(rootCustomerId.equals(parentId)))
//				status="R";
//			else
//			status="D";
//			}
//		DefaultMutableTreeNode node = new DefaultMutableTreeNode(status+""+customerId+" ("+amount+")");
//		root.add(node);
//		if(!(status.equals("R")))
//			getTreeStructure(node);
//		}
//	}

}
