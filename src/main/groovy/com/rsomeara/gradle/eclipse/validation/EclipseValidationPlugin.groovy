/*Copyright 2016 Ryan O'Meara
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rsomeara.gradle.eclipse.validation

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.plugins.ide.eclipse.EclipsePlugin
import org.gradle.plugins.ide.eclipse.model.BuildCommand
import org.gradle.plugins.ide.eclipse.model.EclipseModel
import org.gradle.util.ConfigureUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.rsomeara.gradle.eclipse.validation.model.EclipseValidation

public class EclipseValidationPlugin implements Plugin<Project> {

	private static final Logger logger = LoggerFactory.getLogger(EclipseValidationPlugin.class)

	//Add validation to EclipseModel via meta-classes. Feels a little hack-ish, but allows the user to define validation within the existing Eclipse scope
	static{
		EclipseModel.metaClass.wstValidation = new EclipseValidation()
		EclipseModel.metaClass.validation = {closure -> ConfigureUtil.configure(closure, wstValidation)}
	}

	void apply(Project project) {
		project.task('hello') << { println "Hello from the EclipseValidationPlugin" }

		//Requires the Eclipse plug-in to be applied, as it adds elements to it
		project.pluginManager.apply(EclipsePlugin)

		//Add task dependency
		configureEclipseProject(project)
	}

	private void configureEclipseProject(Project project) {
		project.afterEvaluate{
			EclipseModel eclipse = project.extensions.getByType(EclipseModel)

			if(!eclipse.wstValidation.separateBuilder){
				eclipse.project.file.whenMerged{projectFile ->
					projectFile.buildCommands.removeAll{'org.eclipse.wst.validation.validationbuilder'.equals(it.name)}
				}
			}else{
				boolean found = false

				eclipse.project.buildCommands.forEach{
					found = found || 'org.eclipse.wst.validation.validationbuilder'.equals(it.name)
				}

				if(!found){
					eclipse.project.buildCommands.add(new BuildCommand('org.eclipse.wst.validation.validationbuilder'))
				}
			}
		}
	}
}