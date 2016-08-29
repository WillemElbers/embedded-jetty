package nl.we.embedded.jetty;


import org.jhades.JHades;
import org.junit.Test;

/**
 *
 *  without swagger:
 * 
file:/Users/wilelb/.m2/repository/javax/annotation/javax.annotation-api/1.2/javax.annotation-api-1.2.jar overlaps with 
file:/Library/Java/JavaVirtualMachines/jdk1.8.0_31.jdk/Contents/Home/jre/lib/rt.jar - total overlapping classes: 6 - different classloaders.
 * 
 * with swagger:
 * 
 * 
file:/Users/wilelb/.m2/repository/javax/ws/rs/jsr311-api/1.1.1/jsr311-api-1.1.1.jar overlaps with 
file:/Users/wilelb/.m2/repository/javax/ws/rs/javax.ws.rs-api/2.0.1/javax.ws.rs-api-2.0.1.jar - total overlapping classes: 55 - same classloader ! This is an ERROR!

file:/Users/wilelb/.m2/repository/javax/annotation/javax.annotation-api/1.2/javax.annotation-api-1.2.jar overlaps with 
file:/Library/Java/JavaVirtualMachines/jdk1.8.0_31.jdk/Contents/Home/jre/lib/rt.jar - total overlapping classes: 6 - different classloaders.
* 
 * @author wilelb
 */
public class DependencyTest {

    @Test
    public void testDependencyOverlap() {
        JHades j = new JHades().overlappingJarsReport(); 
    }
    
}
