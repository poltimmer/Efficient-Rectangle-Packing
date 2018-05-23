/*
 * Copyright (c) 2017, alexbonilla
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package nl.tue.algorithms.dbl.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import nl.tue.algorithms.dbl.common.PackData;
import nl.tue.algorithms.dbl.common.PackWidthQueue;
import nl.tue.algorithms.dbl.common.RectangleRotatable;

/**
 *
 * @author alexbonilla
 * from https://github.com/alexbonilla/java-bin-packer
 */
public class BinaryTree {

    private final ArrayList<RectangleRotatable> root = new ArrayList();

    public Packer(int numofpackets, double width, double height) {
        for(int i = 0; i< numofpackets;i++){
            this.root.add(new RectangleRotatable(0, 0, width, height));
        }        
    }
    

    public void fit(ArrayList<RectangleRotatable> blocks) {
        RectangleRotatable r;
        RectangleRotatable block;
        Iterator<RectangleRotatable> blockItr = blocks.iterator();
        int n=0;
        while (blockItr.hasNext()) {
            block = blockItr.next();
            if ((r = this.findRectangleRotatable(this.root.get(n), block.width, block.height))!=null) {
                block.fit = this.splitRectangleRotatable(r, block.width, block.height);
                if(r.isroot){
                    block.fit.isroot = true;
                }
            }else{
                n++;
            }
        }
    }

    public RectangleRotatable findRectangleRotatable(RectangleRotatable root, double width, double height) {
        if (root.used) {
            RectangleRotatable right = findRectangleRotatable(root.right, width, height);
            return (right != null ? right : findRectangleRotatable(root.down, width, height));
        } else if ((width <= root.width) && (height <= root.height)) {
            return root;
        } else {
            return null;
        }
    }

    public RectangleRotatable splitRectangleRotatable(RectangleRotatable r, double width, double height) {
        r.used = true;
        r.down = new RectangleRotatable(r.x, r.y + height, r.width, r.height - height);
        r.right = new RectangleRotatable(r.x + width, r.y, r.width - width, height);
        return r;
    }

}
