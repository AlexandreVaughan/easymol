/*
 * 04/18/2002
 *
 * Angle3D.java - A utility class to manipulate angles in 3D
 * Copyright (C) 2002 Alexandre Vaughan
 * avaughan@altern.org
 * 
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */   

   package fr.emn.easymol.geometry;


/** A simple class to manipulate angles for 3D transforms<br><br>
  * This class converts a vector composed of angles in degrees into radians.
  * For instance, if the object is built like this : <br><br>
  * <code>Angle3D alpha = new Angle3D(60,45,90);</code>
  * The angles returned by the accessors will be : <code>Math.PI/3</code>,
  * <code>Math.PI/4</code> and <code>Math.PI/2</code>.
**/
   public class Angle3D {
      private double angleX; // the angle of rotation for the X axis
      private double angleY; // the angle of rotation for the Y axis
      private double angleZ; // the angle of rotation for the Z axis
   
   /** Builds a new Angle3D
     * @param x the angle of rotation for the X axis in degrees
     * @param y the angle of rotation for the Y axis in degrees
     * @param z the angle of rotation for the Z axis in degrees
   **/
      public Angle3D(double x, double y, double z) {
         angleX=toRadian(x);
         angleY=toRadian(y);
         angleZ=toRadian(z);
      }
   
   // Converts a double from degrees to radian
      private double toRadian(double d) {
         return ((Math.PI*d)/180.0d);
      }
   
   /** Gets the angle of rotation for X in radians
     * @return the angle of rotation
   **/
      public double getAngleX() {
         return angleX;
      }
   /** Gets the angle of rotation for Y in radians
     * @return the angle of rotation
   **/
      public double getAngleY() {
         return angleY;
      }
   /** Gets the angle of rotation for Z in radians
     * @return the angle of rotation
   **/ 
      public double getAngleZ() {
         return angleZ;
      }
   }