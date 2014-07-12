/*
 002.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 003.
 *
 004.
 * Copyright 1997-2008 Sun Microsystems, Inc. All rights reserved.
 005.
 *
 006.
 * The contents of this file are subject to the terms of either the GNU
 007.
 * General Public License Version 2 only ("GPL") or the Common Development
 008.
 * and Distribution License("CDDL") (collectively, the "License").  You
 009.
 * may not use this file except in compliance with the License. You can obtain
 010.
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 011.
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 012.
 * language governing permissions and limitations under the License.
 013.
 *
 014.
 * When distributing the software, include this License Header Notice in each
 015.
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 016.
 * Sun designates this particular file as subject to the "Classpath" exception
 017.
 * as provided by Sun in the GPL Version 2 section of the License file that
 018.
 * accompanied this code.  If applicable, add the following below the License
 019.
 * Header, with the fields enclosed by brackets [] replaced by your own
 020.
 * identifying information: "Portions Copyrighted [year]
 021.
 * [name of copyright owner]"
 022.
 *
 023.
 * Contributor(s):
 024.
 *
 025.
 * If you wish your version of this file to be governed by only the CDDL or
 026.
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 027.
 * elects to include this software in this distribution under the [CDDL or GPL
 028.
 * Version 2] license."  If you don't indicate a single choice of license, a
 029.
 * recipient has the option to distribute your version of this file under
 030.
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 031.
 * its licensees as provided above.  However, if you add GPL Version 2 code
 032.
 * and therefore, elected the GPL Version 2 license, then the option applies
 033.
 * only if the new code is made subject to such option by the copyright
 034.
 * holder.
 035.
 *
 036.
 *
 037.
 * This file incorporates work covered by the following copyright and
 038.
 * permission notice:
 039.
 *
 040.
 * Copyright 2004 The Apache Software Foundation
 041.
 *
 042.
 * Licensed under the Apache License, Version 2.0 (the "License");
 043.
 * you may not use this file except in compliance with the License.
 044.
 * You may obtain a copy of the License at
 045.
 *
 046.
 *     http://www.apache.org/licenses/LICENSE-2.0
 047.
 *
 048.
 * Unless required by applicable law or agreed to in writing, software
 049.
 * distributed under the License is distributed on an "AS IS" BASIS,
 050.
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 051.
 * See the License for the specific language governing permissions and
 052.
 * limitations under the License.
 053.
 */
/**
 056.
 @project JSF Ajax Library
 057.
 @version 2.0
 058.
 @description This is the standard implementation of the JSF Ajax Library.
 059.
 */
if (typeof OpenAjax !== "undefined" &&
        typeof OpenAjax.hub.registerLibrary !== "undefined") {
    OpenAjax.hub.registerLibrary("mojarra", "www.sun.com", "1.0", null);
}
var mojarra = mojarra || {};
/**
 081.
 * This function deletes any hidden parameters added
 082.
 * to the form by checking for a variable called 'adp'
 083.
 * defined on the form.  If present, this variable will
 084.
 * contain all the params added by 'apf'.
 085.
 *
 086.
 * @param f - the target form
 087.
 */
mojarra.dpf = function dpf(f) {
    var adp = f.adp;
    if (adp !== null) {
        for (var i = 0; i < adp.length; i++) {
            f.removeChild(adp[i]);
        }
    }
};
/*
 098.
 * This function adds any parameters specified by the
 099.
 * parameter 'pvp' to the form represented by param 'f'.
 100.
 * Any parameters added will be stored in a variable
 101.
 * called 'adp' and stored on the form.
 102.
 *
 103.
 * @param f - the target form
 104.
 * @param pvp - associative array of parameter
 105.
 *  key/value pairs to be added to the form as hidden input
 106.
 *  fields.
 107.
 */
mojarra.apf = function apf(f, pvp) {
    var adp = new Array();
    f.adp = adp;
    var i = 0;
    for (var k in pvp) {
        if (pvp.hasOwnProperty(k)) {
            var p = document.createElement("input");
            p.type = "hidden";
            p.name = k;
            p.value = pvp[k];
            f.appendChild(p);
            adp[i++] = p;
        }
    }
};
/*
 125.
 * This is called by command link and command button.  It provides
 126.
 * the form it is nested in, the parameters that need to be
 127.
 * added and finally, the target of the action.  This function
 128.
 * will delete any parameters added <em>after</em> the form
 129.
 * has been submitted to handle DOM caching issues.
 130.
 *
 131.
 * @param f - the target form
 132.
 * @param pvp - associative array of parameter
 133.
 *  key/value pairs to be added to the form as hidden input
 134.
 *  fields.
 135.
 * @param t - the target of the form submission
 136.
 */
mojarra.jsfcljs = function jsfcljs(f, pvp, t) {
    mojarra.apf(f, pvp);
    var ft = f.target;
    if (t) {
        f.target = t;
    }
    f.submit();
    f.target = ft;
    mojarra.dpf(f);
};
/*
 149.
 * This is called by functions that need access to their calling
 150.
 * context, in the form of <code>this</code> and <code>event</code>
 151.
 * objects.
 152.
 *
 153.
 *  @param f the function to execute
 154.
 *  @param t this of the calling function
 155.
 *  @param e event of the calling function
 156.
 *  @return object that f returns
 157.
 */
mojarra.jsfcbk = function jsfcbk(f, t, e) {
    return f.call(t, e);
};
/*
 163.
 * This is called by the AjaxBehaviorRenderer script to
 164.
 * trigger a jsf.ajax.request() call.
 165.
 *
 166.
 *  @param s the source element or id
 167.
 *  @param e event of the calling function
 168.
 *  @param n name of the behavior event that has fired
 169.
 *  @param ex execute list
 170.
 *  @param re render list
 171.
 *  @param op options object
 172.
 */
mojarra.ab = function ab(s, e, n, ex, re, op) {
    if (!op) {
        op = {};
    }
    if (n) {
        op["javax.faces.behavior.event"] = n;
    }

    if (ex) {
        op["execute"] = ex;
    }
    if (re) {
        op["render"] = re;
    }

    jsf.ajax.request(s, e, op);
}