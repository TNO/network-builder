package org.tno.networks.graph;

import java.io.PrintWriter;

import org.freehep.util.io.IndentPrintWriter;
import org.tno.networks.graph.Graph.Edge;
import org.tno.networks.graph.Graph.Node;

public class GmlWriter {
	public static void write(InMemoryGraph graph, PrintWriter out) {
		IndentPrintWriter iout = new IndentPrintWriter(out);
		iout.setIndentString("\t");
		int indent = 0;
		
		iout.println("graph [");
		iout.println("directed\t" + (graph.isDirected() ? 1 : 0));
		iout.setIndent(++indent);

		//Print nodes and attributes
		for(Node n : graph.getNodes()) {
			iout.println("node [");
			iout.setIndent(++indent);
			iout.println("id\t" + n.hashCode());
			iout.println("identifier\t" + '"' + n.getId() + '"');
			printAttributes(iout, n);
			iout.setIndent(--indent);
			iout.println("]");
		}
		//Print edges and attributes
		for(Edge e : graph.getEdges()) {
			iout.println("edge [");
			iout.setIndent(++indent);
			String srcS = e.getSrc().getId();
			String tgtS = e.getTgt().getId();
			int src = srcS.hashCode();
			int tgt = tgtS.hashCode();
			iout.println("source\t"  + src);
			iout.println("target\t"  + tgt);
			iout.println("id\t" + e.getId().hashCode());
			iout.println("identifier\t" + e.getId().hashCode());
			printAttributes(iout, e);
			iout.setIndent(--indent);
			iout.println("]");
		}
		//Print network attributes
		printAttributes(iout, graph);
		iout.setIndent(--indent);
		iout.println("]");
	}
	
	private static void printAttributes(PrintWriter out, AttributeHolder attributes) {
		for(String k : attributes.getAttributeNames()) {
			//Find out if this is a number
			Object v = attributes.getAttribute(k);
			if(v == null || "".equals(v)) continue; //Skip empty attributes
			boolean isNumber = v instanceof Number;
			if(!isNumber) {
				String s = v.toString();
				s = s.replaceAll("\"", "");
				s = s.replaceAll("\\n", "");
				v = '"' + s + '"';
			}
			out.println(k + "\t" + v);
		}
	}
}
